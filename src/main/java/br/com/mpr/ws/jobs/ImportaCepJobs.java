package br.com.mpr.ws.jobs;


import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.CepEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImportaCepJobs {

    private static final Log log = LogFactory.getLog(ImportaCepJobs.class);

    @Autowired
    private CommonDao commonDao;


    //RODAR A CADA 2 MESES
    //@Scheduled(fixedRate = 1000*60*60*24*2, initialDelay = -1)
    public void run() {

        //TODO DEIXAR CONFIGURAVEL O ENDERECO DO ARQUIVO DE CEP.
        File fileCeps = new File("/home/wagner/Downloads/ceps.txt");
        try {
            FileReader fileReader = new FileReader(fileCeps);
            BufferedReader reader = new BufferedReader(fileReader);
            int count = 0;
            String line;
            Gerenciador g = new Gerenciador();
            while ( (line = reader.readLine()) != null ){
                log.debug("LINHA = " + ++count);

                Executor executor;
                while ( (executor = g.getExecutorLivre()) == null){
                    try {
                        log.debug("AGUARDANDO POR UMA THREAD LIVRE...");
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                executor.execute(line);

            }


        } catch (FileNotFoundException e) {
            log.error("Arquivo não encontrado = " + fileCeps.getAbsolutePath());
        } catch (IOException e) {
            log.error("Erro ao ler o arquivo = " + fileCeps.getAbsolutePath());
        }


    }

    protected CepEntity createCepEntity(String line) {
        CepEntity cepEntity = new CepEntity();
        String [] dados = line.split("\\t");
        cepEntity.setCep(dados[0]);

        if (dados.length > 1){
            String [] cidadeUf = dados[1].split("/");
            cepEntity.setCidade(cidadeUf[0]);
            cepEntity.setUf(cidadeUf[1]);
        }
        if (dados.length > 2){
            cepEntity.setBairro(dados[2]);
        }

        if (dados.length > 3){
            cepEntity.setLogradouro(dados[3]);
        }
        return cepEntity;
    }

    
    class Gerenciador{

        private List<Executor> executores = new ArrayList<>();

        Gerenciador(){
            for (int i = 0; i < 20; i ++){
                executores.add(new Executor(i+1));
            }
        }

        void execute(String line){
            Executor e = getExecutorLivre();
            e.execute(line);
        }

        private Executor getExecutorLivre() {
            for (Executor e : executores){
                if (!e.estaViva()){
                    e.setViva(true);
                    return e;
                }
            }
            return null;
        }

    }

    class Executor extends Thread{
        private String line;
        private String name;
        private boolean viva = false;
        private Date end;
        private Thread thread;

        Executor(Integer id){
            name = "Thread"+ id;
        }


        public void execute(String line){
            this.line = line;
            thread = new Thread(this);
            thread.start();
        }

        public boolean estaViva(){
            boolean esta = viva || end != null && ((new Date().getTime() - end.getTime()) < 20);
            return esta;
        }
        @Override
        public void run() {

            try{

                CepEntity cep = createCepEntity(line);
                CepEntity cepPesisted = commonDao.get(CepEntity.class, cep.getCep());

                if (cepPesisted != null){
                    BeanUtils.copyProperties(cep,cepPesisted);
                    log.debug(name + " - Alterando cep = " + cep.getCep());
                    commonDao.update(cepPesisted);
                }else{
                    log.debug(name + " - Criando cep = " + cep.getCep());
                    commonDao.save(cep);
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
            this.viva = false;
            end = new Date();
        }

        public void setViva(boolean viva){
            this.viva = viva;
        }
    }

}
