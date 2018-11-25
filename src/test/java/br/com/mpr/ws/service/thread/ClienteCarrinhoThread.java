package br.com.mpr.ws.service.thread;

import br.com.mpr.ws.service.CarrinhoService;
import br.com.mpr.ws.vo.CarrinhoVo;
import br.com.mpr.ws.vo.ItemCarrinhoForm;

/**
 *
 */
public class ClienteCarrinhoThread extends Thread{

    private CarrinhoService carrinhoService;
    private ItemCarrinhoForm form;
    private CarrinhoVo carrinhoVo;
    private String messageException;


    public ClienteCarrinhoThread(CarrinhoService carrinhoService, ItemCarrinhoForm form){
        this.carrinhoService = carrinhoService;
        this.form = form;
    }

    @Override
    public void run() {
        try {
            carrinhoVo = this.carrinhoService.addCarrinho(form);
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            this.messageException = e.getMessage();
        }
    }

    public CarrinhoVo getCarrinhoVo() {
        return carrinhoVo;
    }

    public String getMessageException() {
        return messageException;
    }

}
