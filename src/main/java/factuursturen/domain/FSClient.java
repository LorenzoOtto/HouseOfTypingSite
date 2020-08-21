package factuursturen.domain;

import java.util.Date;

public class FSClient
{
    private int       clientnr;
    private String    contact;
    private boolean   showcontact;
    private String    company;
    private String    address;
    private String    zipcode;
    private String    city;
    private String    country; // eigenlijk een enum van maken
    private String    phone;
    private String    mobile;
    private String    email;
    private String    bankcode;
    private String    biccode;
    private String    taxnumber;
    private boolean   tax_shifted;
    private Date      lastinvoice;
    private String    sendmethod;
    private String    paymentmethod;
    private int       top;
    private String    stddiscount;
    private String    mailintro;
    //    private String[]  reference;
    private String    notes;
    private boolean   notes_on_invoice;
    private boolean   active;
    private String    default_doclang;
    private int       default_email;
    private String    currency;
    private String    mandate_id;
    private Date      mandate_date;
    private String    collecttype;
    private Date      timestamp;
    private String    tax_type;

    public int getClientnr() {
        return clientnr;
    }

    public void setClientnr(int clientnr) {
        this.clientnr = clientnr;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public boolean getShowcontact() {
        return showcontact;
    }

    public void setShowcontact(boolean showcontact) {
        this.showcontact = showcontact;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getBiccode() {
        return biccode;
    }

    public void setBiccode(String biccode) {
        this.biccode = biccode;
    }

    public String getTaxnumber() {
        return taxnumber;
    }

    public void setTaxnumber(String taxnumber) {
        this.taxnumber = taxnumber;
    }

    public boolean getTax_shifted() {
        return tax_shifted;
    }

    public void setTax_shifted(boolean tax_shifted) {
        this.tax_shifted = tax_shifted;
    }

    public Date getLastinvoice() {
        return lastinvoice;
    }

    public void setLastinvoice(Date lastinvoice) {
        this.lastinvoice = lastinvoice;
    }

    public String getSendmethod() {
        return sendmethod;
    }

    public void setSendmethod(String sendmethod) {
        this.sendmethod = sendmethod;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public String getStddiscount() {
        return stddiscount;
    }

    public void setStddiscount(String stddiscount) {
        this.stddiscount = stddiscount;
    }

    public String getMailintro() {
        return mailintro;
    }

    public void setMailintro(String mailintro) {
        this.mailintro = mailintro;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean getNotes_on_invoice() {
        return notes_on_invoice;
    }

    public void setNotes_on_invoice(boolean notes_on_invoice) {
        this.notes_on_invoice = notes_on_invoice;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDefault_doclang() {
        return default_doclang;
    }

    public void setDefault_doclang(String default_doclang) {
        this.default_doclang = default_doclang;
    }

    public int getDefault_email() {
        return default_email;
    }

    public void setDefault_email(int default_email) {
        this.default_email = default_email;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMandate_id() {
        return mandate_id;
    }

    public void setMandate_id(String mandate_id) {
        this.mandate_id = mandate_id;
    }

    public Date getMandate_date() {
        return mandate_date;
    }

    public void setMandate_date(Date mandate_date) {
        this.mandate_date = mandate_date;
    }

    public String getCollecttype() {
        return collecttype;
    }

    public void setCollecttype(String collecttype) {
        this.collecttype = collecttype;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getTax_type() {
        return tax_type;
    }

    public void setTax_type(String tax_type) {
        this.tax_type = tax_type;
    }


}
