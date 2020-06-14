package pl.kmprograms.model;


import java.util.List;
import java.util.Objects;

public class ExchangeRate {
    private String currency;
    private String code;
    private List<Rate> rates;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRate that = (ExchangeRate) o;
        return Objects.equals(currency, that.currency) &&
                Objects.equals(code, that.code) &&
                Objects.equals(rates, that.rates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, code, rates);
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "currency='" + currency + '\'' +
                ", code='" + code + '\'' +
                ", rates=" + rates +
                '}';
    }

    public static ExchangeRateBuilder builder() {
        return new ExchangeRateBuilder();
    }

    public static final class ExchangeRateBuilder {
        private String currency;
        private String code;
        private List<Rate> rates;

        public ExchangeRateBuilder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public ExchangeRateBuilder code(String code) {
            this.code = code;
            return this;
        }

        public ExchangeRateBuilder rates(List<Rate> rates) {
            this.rates = rates;
            return this;
        }

        public ExchangeRate build() {
            ExchangeRate exchangeRate = new ExchangeRate();
            exchangeRate.setCurrency(currency);
            exchangeRate.setCode(code);
            exchangeRate.setRates(rates);
            return exchangeRate;
        }
    }
}
