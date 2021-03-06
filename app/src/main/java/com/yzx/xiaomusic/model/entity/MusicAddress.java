package com.yzx.xiaomusic.model.entity;

import com.google.gson.annotations.SerializedName;
import com.yzx.commonlibrary.base.BaseResposeBody;

import java.util.List;

/**
 * Created by yzx on 2018/6/20.
 * Description
 */
public class MusicAddress extends BaseResposeBody {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 518686034
         * url : http://m10.music.126.net/20180620172234/b433f89bef58e76f85323fc65b1060ca/ymusic/1e9a/6fcb/fbad/ab7163310afd534e125c40729cf61910.mp3
         * br : 128000
         * size : 4702920
         * md5 : ab7163310afd534e125c40729cf61910
         * code : 200
         * expi : 1200
         * type : mp3
         * gain : -1.95
         * fee : 8
         * uf : null
         * payed : 0
         * flag : 0
         * canExtend : false
         */

        private int id;
        private String url;
        private int br;
        private int size;
        private String md5;
        @SerializedName("code")
        private int codeX;
        private int expi;
        private String type;
        private double gain;
        private int fee;
        private Object uf;
        private int payed;
        private int flag;
        private boolean canExtend;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getBr() {
            return br;
        }

        public void setBr(int br) {
            this.br = br;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public int getCodeX() {
            return codeX;
        }

        public void setCodeX(int codeX) {
            this.codeX = codeX;
        }

        public int getExpi() {
            return expi;
        }

        public void setExpi(int expi) {
            this.expi = expi;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public double getGain() {
            return gain;
        }

        public void setGain(double gain) {
            this.gain = gain;
        }

        public int getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }

        public Object getUf() {
            return uf;
        }

        public void setUf(Object uf) {
            this.uf = uf;
        }

        public int getPayed() {
            return payed;
        }

        public void setPayed(int payed) {
            this.payed = payed;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public boolean isCanExtend() {
            return canExtend;
        }

        public void setCanExtend(boolean canExtend) {
            this.canExtend = canExtend;
        }
    }
}
