package br.com.mpr.ws.jobs;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.CepEntity;
import br.com.mpr.ws.entity.EmbalagemEntity;
import br.com.mpr.ws.entity.FreteCepEntity;
import br.com.mpr.ws.entity.FreteType;
import br.com.mpr.ws.service.FreteService;
import br.com.mpr.ws.vo.ResultFreteVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CalculaValorFreteJobs {

    private static final Log log = LogFactory.getLog(CalculaValorFreteJobs.class);


    @Autowired
    private CommonDao commonDao;

    @Autowired
    private FreteService freteService;

    private List<EmbalagemEntity> embalagens;


    //RODAR A CADA 2 MESES
    @Scheduled(fixedRate = 1000*60*60*24*90, initialDelay = -1)
    public void run() {
        embalagens = commonDao.listAll(EmbalagemEntity.class);
        Page<CepEntity> page = commonDao.listAllPaged(CepEntity.class, PageRequest.of(1,5000));

        try {

            while (page.getNumber() < page.getTotalPages()){
                log.debug("PAGINA [" + page.getNumber() + "/" + page.getTotalPages() + "]");
                List<CepEntity> ceps = page.getContent();
                final CalculaValorFreteJobs.Gerenciador gerenciador = new CalculaValorFreteJobs.Gerenciador();
                int item = 1;
                for(CepEntity cep : ceps) {
                    log.debug("FAZENDO ITEM [" + item++ + "/5000] - [" + page.getNumber() + "/" + page.getTotalPages() + "]" );
                    CalculaValorFreteJobs.Executor executor;
                    while ( (executor = gerenciador.getExecutorLivre()) == null){
                        try {
                            log.debug("AGUARDANDO POR UMA THREAD LIVRE...");
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    executor.execute(cep);
                }
                page = commonDao.listAllPaged(CepEntity.class, page.nextPageable());
            }


        } catch (Exception e) {
            log.error("Erro ao buscar um cep" , e);
        }


    }

    class Gerenciador{

        private List<CalculaValorFreteJobs.Executor> executores = new ArrayList<>();

        Gerenciador(){
            for (int i = 0; i < 30; i ++){
                executores.add(new CalculaValorFreteJobs.Executor(i+1));
            }
        }

        private CalculaValorFreteJobs.Executor getExecutorLivre() {
            for (CalculaValorFreteJobs.Executor e : executores){
                if (!e.estaViva()){
                    e.setViva(true);
                    return e;
                }
            }
            return null;
        }

    }

    class Executor extends Thread{
        private CepEntity cep;
        private String name;
        private boolean viva = false;
        private Date end;
        private Thread thread;

        Executor(Integer id){
            name = "Thread"+ id;
        }


        public void execute(CepEntity cep){
            this.cep = cep;
            thread = new Thread(this);
            thread.start();
        }

        public boolean estaViva(){
            boolean esta = viva || end != null && ((new Date().getTime() - end.getTime()) < 20);
            return esta;
        }
        @Override
        public void run() {

            //POR EMBALAGEM
            embalagens.stream().forEach( embalagem -> {
                //POR TIPO DE FRETE
                for(FreteType freteType : FreteType.values()){

                    try{

                        FreteCepEntity freteCep = commonDao.findByPropertiesSingleResult(FreteCepEntity.class,
                                new String[]{"cep","tipoFrete","idEmbalagem"},
                                new Object[]{cep.getCep(),freteType,embalagem.getId()});

                        if (freteCep == null){
                            log.debug(name + " - Calculando frete para " + cep.getCep() + " embalagem = " +
                                    embalagem.getDescricao() + " tipoFrete = " + freteType.getDescricao());
                            ResultFreteVo result = freteService.calcFrete(new FreteService.FreteParam(freteType,
                                    cep.getCep(),
                                    1.0,
                                    embalagem.getComp(),
                                    embalagem.getLarg(),
                                    embalagem.getAlt()
                            ));
                            log.debug(name + " - Fim do calculo do frete");
                            if (result != null && result.getValor() != null &&
                                    ( !result.getMessageError().contains("Erro")) ){
                                freteCep = new FreteCepEntity();
                                freteCep.setCep(cep.getCep());
                                freteCep.setTipoFrete(freteType);
                                freteCep.setValor(result.getValor());
                                freteCep.setDataCalculo(new Date());
                                freteCep.setIdEmbalagem(embalagem.getId());
                                commonDao.save(freteCep);

                            }
                        }
                    }catch(Exception ex){
                        log.error("Erro do calculo do frente", ex);
                    }

                }
            });

            this.viva = false;
            end = new Date();
        }

        public void setViva(boolean viva){
            this.viva = viva;
        }
    }

}