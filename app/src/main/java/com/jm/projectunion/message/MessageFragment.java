package com.jm.projectunion.message;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseFragment;
import com.jm.projectunion.common.utils.ToastUtils;
import com.hyphenate.easeui.widget.swipemenulistview.SwipeMenu;
import com.hyphenate.easeui.widget.swipemenulistview.SwipeMenuCreator;
import com.hyphenate.easeui.widget.swipemenulistview.SwipeMenuItem;
import com.hyphenate.easeui.widget.swipemenulistview.SwipeMenuListView;
import com.jm.projectunion.message.activity.MsgSystemActivity;
import com.jm.projectunion.message.adapter.MessageAdapter;
import com.jm.projectunion.message.chat.ChatActivity;
import com.jm.projectunion.message.chat.helper.Constant;
import com.jm.projectunion.utils.UiGoto;
import com.zhy.autolayout.utils.AutoUtils;

import butterknife.BindView;

/**
 * Created by Young on 2017/10/26.
 */

public class MessageFragment extends BaseFragment {

    @BindView(R.id.msglist)
    SwipeMenuListView msglist;

    private MessageAdapter msgAdapter;

    @Override
    public void initView(View view) {
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_message_header, null);
        headerView.findViewById(R.id.msg_system).setOnClickListener(this);
        headerView.findViewById(R.id.msg_industry).setOnClickListener(this);
        headerView.findViewById(R.id.msg_data).setOnClickListener(this);
        AutoUtils.autoSize(headerView);
        msglist.addHeaderView(headerView);
        msgAdapter = new MessageAdapter(getActivity());
        msglist.setAdapter(msgAdapter);
        registerForContextMenu(msglist);
        initListView();
//        loginIM();
    }

    @Override
    public void initData() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_message;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.msg_system:
                UiGoto.startAtyWithType(getActivity(), MsgSystemActivity.class, MsgSystemActivity.MSG_SYS);
                break;
            case R.id.msg_industry:
                UiGoto.startAtyWithType(getActivity(), MsgSystemActivity.class, MsgSystemActivity.MSG_INDU);
                break;
            case R.id.msg_data:
                UiGoto.startAtyWithType(getActivity(), MsgSystemActivity.class, MsgSystemActivity.MSG_DATA);
                break;
        }
    }

    private void initListView() {
        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(getActivity().getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.parseColor("#E11E1E")));
                // set item width
                openItem.setWidth(130);
                // set item title
                openItem.setTitle("删除");
                // set item title fontsize
                openItem.setTitleSize(12);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);
            }
        };

        // set creator
        msglist.setMenuCreator(creator);

        // step 2. listener item click event
        msglist.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        ToastUtils.showShort(getActivity(), "删除");
                        break;
                }
                return false;
            }
        });

        msglist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                startActivity(new Intent(getActivity(), ChatActivity.class).putExtra(Constant.EXTRA_USER_ID, "robotone"));
            }
        });
    }
}