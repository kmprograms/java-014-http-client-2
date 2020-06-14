package pl.kmprograms.model;

import java.util.Objects;

public class Rate {
    private String no;
    private String effectiveDate;
    private String bid;
    private String ask;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rate rate = (Rate) o;
        return Objects.equals(no, rate.no) &&
                Objects.equals(effectiveDate, rate.effectiveDate) &&
                Objects.equals(bid, rate.bid) &&
                Objects.equals(ask, rate.ask);
    }

    @Override
    public int hashCode() {
        return Objects.hash(no, effectiveDate, bid, ask);
    }

    @Override
    public String toString() {
        return "Rate{" +
                "no='" + no + '\'' +
                ", effectiveDate='" + effectiveDate + '\'' +
                ", bid='" + bid + '\'' +
                ", ask='" + ask + '\'' +
                '}';
    }

    public static RateBuilder builder() {
        return new RateBuilder();
    }

    public static final class RateBuilder {
        private String no;
        private String effectiveDate;
        private String bid;
        private String ask;

        public RateBuilder no(String no) {
            this.no = no;
            return this;
        }

        public RateBuilder effectiveDate(String effectiveDate) {
            this.effectiveDate = effectiveDate;
            return this;
        }

        public RateBuilder bid(String bid) {
            this.bid = bid;
            return this;
        }

        public RateBuilder ask(String ask) {
            this.ask = ask;
            return this;
        }

        public Rate build() {
            Rate rate = new Rate();
            rate.setNo(no);
            rate.setEffectiveDate(effectiveDate);
            rate.setBid(bid);
            rate.setAsk(ask);
            return rate;
        }
    }
}
