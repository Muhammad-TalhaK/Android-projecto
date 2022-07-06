package com.example.firebasecrud;

import android.os.Parcel;
import android.os.Parcelable;

public class Employee implements Parcelable {

    private String cname, cprice, csuitedfor, cimglink, clink, cdesc, cid;

    public Employee() {
    }


    protected Employee(Parcel in) {
        cname = in.readString();
        cprice = in.readString();
        csuitedfor = in.readString();
        cimglink = in.readString();
        clink = in.readString();
        cdesc = in.readString();
        cid = in.readString();
    }

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCprice() {
        return cprice;
    }

    public void setCprice(String cprice) {
        this.cprice = cprice;
    }

    public String getCsuitedfor() {
        return csuitedfor;
    }

    public void setCsuitedfor(String csuitedfor) {
        this.csuitedfor = csuitedfor;
    }

    public String getCimglink() {
        return cimglink;
    }

    public void setCimglink(String cimglink) {
        this.cimglink = cimglink;
    }

    public String getClink() {
        return clink;
    }

    public void setClink(String clink) {
        this.clink = clink;
    }

    public String getCdesc() {
        return cdesc;
    }

    public void setCdesc(String cdesc) {
        this.cdesc = cdesc;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }


    public Employee(String cname, String cprice, String csuitedfor, String cimglink, String clink, String cdesc, String cid) {
        this.cname = cname;
        this.cprice = cprice;
        this.csuitedfor = csuitedfor;
        this.cimglink = cimglink;
        this.clink = clink;
        this.cdesc = cdesc;
        this.cid = cid;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cname);
        dest.writeString(cprice);
        dest.writeString(csuitedfor);
        dest.writeString(cimglink);
        dest.writeString(clink);
        dest.writeString(cdesc);
        dest.writeString(cid);
    }
}
