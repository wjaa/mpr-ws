package br.com.mpr.ws.mock;

import br.com.mpr.ws.utils.StringUtils;
import br.com.uol.pagseguro.api.common.domain.Address;
import br.com.uol.pagseguro.api.common.domain.Document;
import br.com.uol.pagseguro.api.common.domain.Phone;
import br.com.uol.pagseguro.api.common.domain.Sender;

import java.util.List;

public class SenderMock implements Sender {



    @Override
    public String getEmail() {
        return StringUtils.createRandomHash() + "@" + StringUtils.createRandomHash() + ".com";
    }

    @Override
    public String getName() {
        return StringUtils.createRandomHash();
    }

    @Override
    public Phone getPhone() {
        return new Phone() {
            @Override
            public String getAreaCode() {
                return "11";
            }

            @Override
            public String getNumber() {
                return StringUtils.createRandomNumber();
            }
        };
    }

    @Override
    public Address getAddress() {
        return null;
    }

    @Override
    public String getCpf() {
        return StringUtils.createRandomNumber();
    }

    @Override
    public String getHash() {
        return null;
    }

    @Override
    public String getIp() {
        return StringUtils.createRandomNumber();
    }

    @Override
    public List<? extends Document> getDocuments() {
        return null;
    }
}
