package com.abc.recorder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abc.recorder.Internetconnection;
import com.abc.recorder.R;
import com.abc.recorder.contacts.ContactProvider;
import com.abc.recorder.pojo_classes.Contacts;
import com.abc.recorder.utils.StringUtils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class IncommingAdapter extends RecyclerView.Adapter{
    private static ArrayList<Object> contacts=new ArrayList<>();
    private final int VIEW1 = 0, VIEW2 = 1,VIEW3=3;
    static IncommingAdapter.itemClickListener listener;
    Context ctx;
    private static final String PREFERENCES_KEY = "recreate keysslsjldsjfl";
    private static final String FIVES_TIMES_CLICK_AD = "27518";
    AdView adView;

    public IncommingAdapter(AdView adView){
        this.adView = adView;
        Log.d("sometag", "IncommingAdapter: "+adView);

    }

    public void setListener(itemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder1;
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());

        Log.d("sometag", "onCreateViewHolder: "+adView);
        switch (viewType) {
            case VIEW1:
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.people_contact,parent,false);
                viewHolder1 = new IncommingAdapter.MyViewHolder(view, adView);
                ctx=view.getContext();
                break;
            case VIEW2:
                View v2 = inflater.inflate(R.layout.no_contact_list,parent, false);
                viewHolder1= new IncommingAdapter.MyViewHolder(v2,adView);
                ctx=v2.getContext();
                break;
            case VIEW3:
                View v3=inflater.inflate(R.layout.time_row,parent,false);
                viewHolder1=new MytimeViewHolder2(v3);
                ctx=v3.getContext();
                break;
            default:
                View v = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                viewHolder1 = new IncommingAdapter.MyViewHolder(v,adView);
                ctx=v.getContext();
                break;
        }
        return  viewHolder1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case VIEW1:
                Contacts contact= (Contacts) contacts.get(position);
                String Phonnumber= StringUtils.prepareContacts(ctx,contact.getNumber());
                if(ContactProvider.checkFav(ctx,Phonnumber)){
                    //not favourite
                    ((MyViewHolder)holder).favorite.setImageResource(R.drawable.ic_star_border_black_24dp);
                }else{
                    //favourite
                    ((MyViewHolder)holder).favorite.setImageResource(R.drawable.ic_star_black_24dp);
                }
                if(ContactProvider.checkContactToRecord(ctx,Phonnumber)){
                    //record
                    ((MyViewHolder)holder).state.setImageResource(R.drawable.ic_microphone);
                }else{
                    //dont wanna record
                    ((MyViewHolder)holder).state.setImageResource(R.drawable.ic_muted);
                }
                ((MyViewHolder)holder).name.setText(contact.getName());
                ((MyViewHolder)holder).number.setText(contact.getNumber());
                if(contact.getPhotoUri()!=null){
                    Picasso.with(ctx)
                            .load(contact.getPhotoUri()).placeholder(R.drawable.profile)
                            .into(((MyViewHolder) holder).profileimage);
                }else {
                    ((MyViewHolder)holder).profileimage.setImageResource(R.drawable.profile);
                }
                ((MyViewHolder)holder).time.setText(contact.getTime());
                break;
            case VIEW2:
                Contacts contact3= (Contacts) contacts.get(position);
                String phonenumber=StringUtils.prepareContacts(ctx,contact3.getNumber());
                if(ContactProvider.checkFav(ctx,phonenumber)){
                    //not favourite
                    ((MyViewHolder)holder).favorite.setImageResource(R.drawable.ic_star_border_black_24dp);
                }else{
                    //favourite
                    ((MyViewHolder)holder).favorite.setImageResource(R.drawable.ic_star_black_24dp);
                }
                if(ContactProvider.checkContactToRecord(ctx,phonenumber)){
                    //record
                    ((MyViewHolder)holder).state.setImageResource(R.drawable.ic_microphone);
                }else{
                    //dont wanna record
                    ((MyViewHolder)holder).state.setImageResource(R.drawable.ic_muted);
                }
                ((MyViewHolder)holder).name.setText(contact3.getNumber());
                ((MyViewHolder)holder).time.setText(contact3.getTime());
                break;
            case VIEW3:
                String time=contacts.get(position).toString();
                if(time=="1"){
                    ((MytimeViewHolder2)holder).time.setText("Today");
                }else if(time=="2"){
                    ((MytimeViewHolder2)holder).time.setText("Yesterday");
                }else{
                    ((MytimeViewHolder2)holder).time.setText(time);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return IncommingAdapter.contacts.size();
    }





    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CircleImageView profileimage;
        TextView name;
        TextView number;
        TextView time;
        ImageView state,favorite;
        AdView adView;
        public MyViewHolder(final View itemView, final AdView adView) {
            super(itemView);
            this.adView = adView;
            profileimage=(CircleImageView)itemView.findViewById(R.id.profile_image);
            name=(TextView)itemView.findViewById(R.id.textView2);
            number=(TextView)itemView.findViewById(R.id.textView3);
            time=(TextView)itemView.findViewById(R.id.textView4);
            state=(ImageView)itemView.findViewById(R.id.imageView5);
            favorite=(ImageView)itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int clickCounter = itemView
                            .getContext()
                            .getSharedPreferences(PREFERENCES_KEY,
                                    MODE_PRIVATE)
                            .getInt(FIVES_TIMES_CLICK_AD, 1);

                    itemView.getContext().getSharedPreferences(PREFERENCES_KEY,
                            Context.MODE_PRIVATE)
                            .edit()
                            .putInt(FIVES_TIMES_CLICK_AD, ++clickCounter)
                            .apply();
                    Log.d("sometag", "onClick: "+clickCounter);


                    if (clickCounter >= 2){
                        // show ad banner
                        admobbanner(itemView.getContext(),adView);
                        // reset counter to zero
                        itemView.getContext().getSharedPreferences(PREFERENCES_KEY,
                                Context.MODE_PRIVATE)
                                .edit()
                                .putInt(FIVES_TIMES_CLICK_AD, 0)
                                .apply();

                    }

                    listener.onClick(view,getAdapterPosition());

                }
            });
        }

        private void admobbanner(Context context, AdView mAdMobAdView ) {

            if (Internetconnection.checkConnection(context)) {

//            mAdMobAdView = (AdView) findViewById(R.id.admob_adview);
                AdRequest adRequest = new AdRequest.Builder()
                        .build();
                Log.d("sometag", "admobbanner: "+mAdMobAdView);
                mAdMobAdView.loadAd(adRequest);
                final InterstitialAd mInterstitial = new InterstitialAd(context);
                mInterstitial.setAdUnitId(context.getString(R.string.interstitial_ad_unit));
                mInterstitial.loadAd(new AdRequest.Builder().build());
                mInterstitial.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        // TODO Auto-generated method stub
                        super.onAdLoaded();
                        if (mInterstitial.isLoaded()) {
                            mInterstitial.show();
                        }
                    }
                });

            } else {
//            AdView mAdMobAdView = (AdView) findViewById(R.id.admob_adview);
                mAdMobAdView.setVisibility(View.GONE);
            }

        }

    }
    public static class MytimeViewHolder2 extends RecyclerView.ViewHolder{
        TextView time;
        public MytimeViewHolder2(View itemView) {
            super(itemView);
            time=(TextView)itemView.findViewById(R.id.time_view);
        }
    }
    @Override
    public int getItemViewType(int position) {
        if(contacts.get(position) instanceof String){
            return VIEW3;
        }else{
            if(contacts.get(position) instanceof Contacts){
                Contacts contxt= (Contacts) contacts.get(position);
                if(contxt.getName()!=null){
                    return VIEW1;
                }else{
                    return VIEW2;
                }
            }else {
                return VIEW1;
            }
        }
    }
    public void setContacts(ArrayList<Object> contacts){
        IncommingAdapter.contacts=contacts;
    }

    public interface itemClickListener{
        public void onClick(View v,int position);
    }


}