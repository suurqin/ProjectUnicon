package com.jm.projectunion.common.entity;

import java.util.List;

/**
 * Created by ${mengyangguang} on 2017/1/5.
 */

public class LianDongEntity {
    private String id;
    private String name;
    private List<Second> mSecondList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Second> getSecondList() {
        return mSecondList;
    }

    public void setSecondList(List<Second> secondList) {
        mSecondList = secondList;
    }

    public static class Second{
        private String id;
        private String name;
        private List<Three> mThrees;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Three> getThrees() {
            return mThrees;
        }

        public void setThrees(List<Three> threes) {
            mThrees = threes;
        }

        public static class Three{
            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
