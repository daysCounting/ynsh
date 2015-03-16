package com.bank.root.myapplication.ListView;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import Util.ConnectionChangeReceiver;
import com.bank.root.myapplication.GetInformation;
import com.bank.root.myapplication.R;
import com.bank.root.myapplication.bean.LocalAddress;
import com.bank.root.myapplication.bean.Node;

import java.util.List;

/**
 * Created by root on 15-3-3.
 */
public class SimpleTreeAdapter<T> extends TreeListViewAdapter<T> {
    ConnectionChangeReceiver myReceiver = new ConnectionChangeReceiver();

    public SimpleTreeAdapter(ListView tree, Context context,
                             List<T> datas, int defaultExpandLevel)
            throws IllegalArgumentException, IllegalAccessException {
        super(tree, context, datas, defaultExpandLevel);
    }

    @Override
    public View getConvertView(final Node node, final int position, View convertView,
                               ViewGroup parent) {
        ViewHolder holder = null;


        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.fragment_one_listview, parent, false);
            holder = new ViewHolder();
            holder.mIcon = (ImageView) convertView
                    .findViewById(R.id.id_treenode_icon);
            holder.mText = (TextView) convertView
                    .findViewById(R.id.id_treenode_label);
            holder.behindText = (TextView) convertView
                    .findViewById(R.id.textView2);
            holder.ry = (RelativeLayout) convertView.
                    findViewById(R.id.Ry);
            holder.ry1 = (RelativeLayout) convertView.
                    findViewById(R.id.Ry1);
            holder.ly = (LinearLayout) convertView.
                    findViewById(R.id.Ly);
            holder.kidText = (TextView) convertView.
                    findViewById(R.id.id_kid);
            holder.takePhoto = (Button) convertView.
                    findViewById(R.id.takePhoto);
            holder.upLoad = (Button) convertView.
                    findViewById(R.id.upLoad);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.ry.setVisibility(View.GONE);

        if (node.getLevel() < 2) {
            holder.mText.setText(node.getName());
            holder.mIcon.setImageResource(node.getIcon());
            if (node.getLevel() == 1) {
                holder.behindText.setText("(" + node.getChildren().size() + ")");
            } else {
                holder.behindText.setText("");
            }
            holder.ly.setVisibility(View.VISIBLE);
            holder.ry1.setVisibility(View.GONE);
            holder.ly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expandOrCollapse(position);
                }
            });

        } else {
            holder.ry1.setVisibility(View.VISIBLE);
            holder.ly.setVisibility(View.GONE);
            holder.kidText.setText(node.getName());
            if (!node.getParent().getName().equals("正在进行")) {
                holder.ry1.setClickable(false);
            }else{
                mClick(holder, node.getLevel(), position);

                if(mLocalAddress != null)
                    toolClick(holder, node);
            }
        }
        return convertView;
    }

    public void mClick(ViewHolder holder, int id, int position) {

        final ViewHolder finalHolder = holder;
        holder.ry1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Animation animation;
                if (finalHolder.ry.getVisibility() == View.GONE) {
                    animation = AnimationUtils.loadAnimation(
                            mContext, R.anim.show);
                    finalHolder.ry.setVisibility(View.VISIBLE);
                    finalHolder.ry.startAnimation(animation);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                            finalHolder.ry.clearAnimation();

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                } else {
                    animation = AnimationUtils.loadAnimation(
                            mContext, R.anim.hiden);
                    finalHolder.ry.startAnimation(animation);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            finalHolder.ry.setVisibility(View.GONE);
                            finalHolder.ry.clearAnimation();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                }
            }

        });
    }


    public void toolClick(ViewHolder holder, Node node) {


        final ViewHolder finalHolder = holder;
        final Node finalNode = node;
        holder.takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myReceiver.isNet()) {
                    Intent intent = new Intent("com.litreily.activity_second");
                    intent.putExtra("Name", finalNode.getName());
                    intent.putExtra("Latitude", mLocalAddress.getLatitude());
                    intent.putExtra("Longitude", mLocalAddress.getLongitude());
                    intent.putExtra("LocalAddress", mLocalAddress.getLocalAddress());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setClass(mContext, GetInformation.class);
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext,
                            "亲，网络连了么!!", Toast.LENGTH_LONG)
                            .show();
                }


            }
        });

    }

    public void setLocalAddress(LocalAddress localAddress) {
        mLocalAddress = localAddress;
    }

    private class ViewHolder {
        ImageView mIcon;
        TextView mText;
        TextView behindText;
        RelativeLayout ry, ry1;
        LinearLayout ly;
        TextView kidText;
        Button takePhoto;
        Button upLoad;
    }


}