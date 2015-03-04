package com.bank.root.myapplication.ListView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bank.root.myapplication.R;
import com.bank.root.myapplication.bean.Node;

import java.util.List;

/**
 * Created by root on 15-3-3.
 */
public class SimpleTreeAdapter<T> extends TreeListViewAdapter<T> {
    public SimpleTreeAdapter(ListView tree, Context context,
                             List<T> datas, int defaultExpandLevel)
            throws IllegalArgumentException, IllegalAccessException {
        super(tree, context, datas, defaultExpandLevel);
    }

    @Override
    public View getConvertView(Node node, int position, View convertView,
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
           holder.ry = (RelativeLayout) convertView.findViewById(R.id.Ry);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mIcon.setImageResource(node.getIcon());

        if (node.getLevel() == 1 ) {
            holder.behindText.setText("(" + node.getChildren().size() + ")");
        }else{
            holder.behindText.setText("");
        }

        holder.mText.setText(node.getName());

        if (node.getLevel() != 2)
            holder.ry.setVisibility(View.GONE);
        else
            holder.ry.setVisibility(View.GONE);
        return convertView;
    }

    public void showAndHide(){}


    private class ViewHolder {
        ImageView mIcon;
        TextView mText;
        TextView behindText;
        RelativeLayout ry;
    }
}