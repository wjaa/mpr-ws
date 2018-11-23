package br.com.mpr.ws.service.thread;

import br.com.mpr.ws.service.CarrinhoService;
import br.com.mpr.ws.vo.ItemCarrinhoForm;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ClienteCarrinhoThreadMonitor {

    private List<ClienteCarrinhoThread> poolThread = new ArrayList<>();


    public void addThread(CarrinhoService carrinhoService, ItemCarrinhoForm form){
        this.poolThread.add(new ClienteCarrinhoThread(carrinhoService, form));
    }

    public void start(){
        for (ClienteCarrinhoThread thread : poolThread){
            thread.start();
        }
    }

    public boolean isAlive(){
        boolean alive = false;

        for (ClienteCarrinhoThread thread : poolThread){
            alive |= thread.isAlive();
        }

        return alive;
    }

    public int getQuantidadeError(){
        int count = 0;

        for (ClienteCarrinhoThread thread : poolThread){
            count += thread.getMessageException() != null &&
                     thread.getMessageException().contains("Infelizmente") ? 1 : 0;
        }

        return count;
    }

    public int getQuantidadeSucesso(){
        int count = 0;

        for (ClienteCarrinhoThread thread : poolThread){
            count += thread.getCarrinhoVo() != null ? 1 : 0;
        }

        return count;
    }


}
