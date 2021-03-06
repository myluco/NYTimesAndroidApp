package com.myluco.nytimessearch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myluco.nytimessearch.R;
import com.myluco.nytimessearch.model.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lcc on 2/13/16.
 */
public class ArticleArrayAdapter extends ArrayAdapter<Article> {
//    private ImageView ivDefault;

    public ArticleArrayAdapter(Context context, List<Article> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
//        ivDefault = new ImageView(context);
//        Picasso.with(getContext()).load(Article.DEFAULT_IMAGE_URL).into(ivDefault);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get  data item
        Article article = getItem(position);

        //if being reused
        //not being reused, need to inflate layout
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.article_layout, parent, false);
        }

        //find image view
        ImageView ivArticle =(ImageView) convertView.findViewById(R.id.ivArticle);
        //clear recycled image
        ivArticle.setImageResource(0);


        TextView tvArticle = (TextView) convertView.findViewById(R.id.tvArticle);

        tvArticle.setText(article.getHeadline());

        if (!article.getThumbnailUrl().isEmpty()) {
            Picasso.with(getContext()).load(article.getThumbnailUrl()).into(ivArticle);
        }else {
            Picasso.with(getContext()).load(Article.DEFAULT_IMAGE_URL).into(ivArticle);
        }

        return convertView;
    }
}
