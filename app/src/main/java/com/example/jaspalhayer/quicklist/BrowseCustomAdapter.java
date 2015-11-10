package com.example.jaspalhayer.quicklist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by jaspalhayer on 07/11/2015.
 */
public class BrowseCustomAdapter extends BaseAdapter {
    List<BrowseRowItem> browseRowItems;
    Context context;

    BrowseCustomAdapter(Context context, List<BrowseRowItem> browseRowItems) {
        this.context = context;
        this.browseRowItems = browseRowItems;
    }

    @Override
    public int getCount() {
        return browseRowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return browseRowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return browseRowItems.indexOf(getItem(position));
    }

    private class ViewHolder {
        ImageView book_pic;
        TextView book_title;
        TextView author_text;
        TextView price_text;
        TextView date_text;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.browse_list_item, null);
            holder = new ViewHolder();

            holder.book_title = (TextView) convertView.findViewById(R.id.book_title);
           // holder.book_pic = (ImageView) convertView.findViewById(R.id.book_pic);
            holder.author_text = (TextView) convertView.findViewById(R.id.author_text);
            holder.date_text = (TextView) convertView.findViewById(R.id.date_text);
            holder.price_text = (TextView) convertView.findViewById(R.id.price_text);

            BrowseRowItem row_pos = browseRowItems.get(position);

            holder.book_title.setText(row_pos.bookTitle);
            holder.author_text.setText(row_pos.bookAuthor);
            holder.price_text.setText("£"+row_pos.bookPrice);
            holder.date_text.setText("("+row_pos.dateListed+")");

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
}




