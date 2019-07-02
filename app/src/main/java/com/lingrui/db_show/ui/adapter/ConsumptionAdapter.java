package com.lingrui.db_show.ui.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lingrui.db_show.R;
import com.lingrui.db_show.dbbean.OutStockBean;
import com.lingrui.db_show.util.CollectionUtil;
import com.lingrui.db_show.util.Flog;

import java.util.ArrayList;
import java.util.List;

public class ConsumptionAdapter extends RecyclerView.Adapter<ConsumptionAdapter.ViewHolder> {
    private final Context mContext;
    private final List<OutStockBean> mData = new ArrayList<>();

    public ConsumptionAdapter(Context mContext, List<OutStockBean> list) {
        this.mContext = mContext;
        this.mData.clear();
        if (CollectionUtil.isNotEmpty(list)) {
            this.mData.addAll(list);
        }

        Flog.d("ConsumptionAdapter", "size:" + this.mData.size());
    }

    public void updateData(List<OutStockBean> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }

        this.mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_consumption, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        OutStockBean infoBean = mData.get(position);
        if (infoBean == null) {
            return;
        }

        if (infoBean.getOut_count() > 0) {
            viewHolder.nameTv.setText(infoBean.getProduct_name());
            viewHolder.countTv.setText(String.valueOf(infoBean.getOut_count()));
            if (!TextUtils.isEmpty(infoBean.getOut_time())) {
                viewHolder.timeTv.setText(infoBean.getOut_time());
            }
            if (!TextUtils.isEmpty(infoBean.getOut_info())) {
                viewHolder.infoTv.setText(infoBean.getOut_info());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv;
        TextView countTv;
        TextView timeTv;
        TextView infoTv;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.product_name_tv);
            countTv = itemView.findViewById(R.id.product_count_tv);
            timeTv = itemView.findViewById(R.id.time_tv);
            infoTv = itemView.findViewById(R.id.info_tv);
        }
    }
}
