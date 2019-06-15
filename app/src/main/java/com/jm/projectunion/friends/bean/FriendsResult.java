package com.jm.projectunion.friends.bean;

import com.jm.projectunion.entity.Result;
import com.jm.projectunion.friends.bean.FriendBean;
import com.jm.projectunion.friends.bean.FriendRect;

import java.io.Serializable;
import java.util.List;

/**
 * Created by YangPan on 2017/12/24.
 */

public class FriendsResult extends Result {

    private FriendData data;

    public FriendData getData() {
        return data;
    }

    public void setData(FriendData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FriendsResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class FriendData implements Serializable {
        private List<FriendBean> friendList;
        private List<FriendRect> recList;

        public List<FriendBean> getFriendList() {
            return friendList;
        }

        public void setFriendList(List<FriendBean> friendList) {
            this.friendList = friendList;
        }

        public List<FriendRect> getRecList() {
            return recList;
        }

        public void setRecList(List<FriendRect> recList) {
            this.recList = recList;
        }

        @Override
        public String toString() {
            return "FriendData{" +
                    "friendList=" + friendList +
                    ", recList=" + recList +
                    '}';
        }
    }
}
