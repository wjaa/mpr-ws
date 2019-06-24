package br.com.mpr.ws.mock;

import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.utils.StringUtils;
import br.com.uol.pagseguro.api.common.domain.*;
import br.com.uol.pagseguro.api.transaction.search.TransactionDetail;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TransactionDetailMock implements TransactionDetail {

    @Override
    public String getReference() {
        return null;
    }

    @Override
    public Date getDate() {
        return null;
    }

    @Override
    public Date getLastEvent() {
        return null;
    }

    @Override
    public BigDecimal getGrossAmount() {
        return null;
    }

    @Override
    public BigDecimal getDiscountAmount() {
        return null;
    }

    @Override
    public BigDecimal getNetAmount() {
        return null;
    }

    @Override
    public BigDecimal getExtraAmount() {
        return null;
    }

    @Override
    public String getPaymentLink() {
        return "http://fake.com.br";
    }

    @Override
    public TransactionPaymentMethod getPaymentMethod() {
        return null;
    }

    @Override
    public TransactionStatus getStatus() {
        return new TransactionStatus(1);
    }

    @Override
    public TransactionType getType() {
        return null;
    }

    @Override
    public Sender getSender() {
        return new SenderMock();
    }

    @Override
    public Shipping getShipping() {
        return null;
    }

    @Override
    public List<? extends PaymentItem> getItems() {
        return null;
    }

    @Override
    public BigDecimal getFeeAmount() {
        return null;
    }

    @Override
    public String getMode() {
        return null;
    }

    @Override
    public TransactionMethod getMethod() {
        return null;
    }

    @Override
    public Date getEscrowEndDate() {
        return null;
    }

    @Override
    public String getCancellationSource() {
        return null;
    }

    @Override
    public CreditorFee getCreditorFees() {
        return null;
    }

    @Override
    public String getCode() {
        return StringUtils.createRandomHash();
    }

    @Override
    public List<? extends Parameter> getParameters() {
        return null;
    }
}