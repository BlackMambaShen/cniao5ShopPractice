package com.example.testing.cniao5shop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.testing.cniao5shop.R;
import com.example.testing.cniao5shop.bean.Campaign;
import com.example.testing.cniao5shop.bean.HomeCampaign;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter {
    private static final int BANNER = 0;
    private static final int VIEW_TYPE_L = 1;
    private static final int VIEW_TYPE_R = 2;
    private  LayoutInflater inflater;
    private int currentType=BANNER;
    private Context mContext;
    private List<com.example.testing.cniao5shop.bean.Banner>banners;
    private List<HomeCampaign>mHomeCampaigns;
    private OnCampaignClickListener mListener;
    public void setOnCampaignClickListener(OnCampaignClickListener listener){
        this.mListener=listener;
    }
    public HomeAdapter(Context context, List<com.example.testing.cniao5shop.bean.Banner> mBanner, List<HomeCampaign> homeCampaigns) {
         mContext=context;
         banners=mBanner;
         mHomeCampaigns=homeCampaigns;
         inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i==BANNER){
            View view = inflater.inflate(R.layout.banner_item,null);
            return new HomeViewHolder(view);
        }else if (i==VIEW_TYPE_R){
            View view = inflater.inflate(R.layout.template_home_cardview2, viewGroup, false);
            return new ItemViewHolder(view);
        }else if (i==VIEW_TYPE_L){
            View view = inflater.inflate(R.layout.template_home_cardview, viewGroup, false);
            return new ItemViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            if (viewHolder instanceof HomeViewHolder){
                HomeViewHolder homeViewHolder= (HomeViewHolder) viewHolder;
                Banner banner=homeViewHolder.banner;
                banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                banner.setBannerAnimation(Transformer.Accordion);
                List<String> imagesUrl=new ArrayList<>();
                for (int j = 0; j <banners.size() ; j++) {
                    com.example.testing.cniao5shop.bean.Banner banner1 = banners.get(j);
                    imagesUrl.add(banner1.getImgUrl());
                }
                banner.setImages(imagesUrl).setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        Glide.with(context).load(path).into(imageView);
                    }
                }).start();
                banner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        Toast.makeText(mContext, "位置"+position, Toast.LENGTH_SHORT).show();
                    }
                });
            }else if (viewHolder instanceof ItemViewHolder){
                ItemViewHolder itemViewHolder= (ItemViewHolder) viewHolder;
                HomeCampaign campaign = mHomeCampaigns.get(i-1);
                itemViewHolder.textTitle.setText(campaign.getTitle());
                Glide.with(mContext).load(campaign.getCpOne().getImgUrl()).into(itemViewHolder.imageViewBig);
                Glide.with(mContext).load(campaign.getCpTwo().getImgUrl()).into(itemViewHolder.imageViewSmallTop);
                Glide.with(mContext).load(campaign.getCpThree().getImgUrl()).into(itemViewHolder.imageViewSmallBottom);
            }
    }

    @Override
    public int getItemCount() {
        return 1+mHomeCampaigns.size();
    }

    @Override
    public int getItemViewType(int position) {
//        switch (position){
//            case 0:
//                currentType=BANNER;
//                break;
//                default:
//                currentType=HOME;
//                break;
//        }
        if (position==0){
            currentType=BANNER;
        }else if (position%2==0||position>0){
            currentType=VIEW_TYPE_R;
        }else {
            currentType=VIEW_TYPE_L;
        }
        return currentType;
    }

    class HomeViewHolder extends RecyclerView.ViewHolder{

        private Banner banner;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
             banner = (Banner)itemView.findViewById(R.id.banner);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textTitle;
        ImageView imageViewBig;
        ImageView imageViewSmallTop;
        ImageView imageViewSmallBottom;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = (TextView) itemView.findViewById(R.id.text_title);
            imageViewBig = (ImageView) itemView.findViewById(R.id.imgview_big);
            imageViewSmallTop = (ImageView) itemView.findViewById(R.id.imgview_small_top);
            imageViewSmallBottom = (ImageView) itemView.findViewById(R.id.imgview_small_bottom);

            imageViewBig.setOnClickListener(this);
            imageViewSmallTop.setOnClickListener(this);
            imageViewSmallBottom.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            HomeCampaign homeCampaign = mHomeCampaigns.get(getLayoutPosition()-1);
            if (mListener!=null){
                switch (v.getId()){
                    case R.id.imgview_big:
                        mListener.onCLick(v,homeCampaign.getCpOne());
                        break;
                    case R.id.imgview_small_top:
                        mListener.onCLick(v,homeCampaign.getCpTwo());
                        break;
                    case R.id.imgview_small_bottom:
                        mListener.onCLick(v,homeCampaign.getCpThree());
                        break;
                }
            }
        }
    }

    public interface OnCampaignClickListener{
        void onCLick(View view, Campaign campaign);
    }
}
