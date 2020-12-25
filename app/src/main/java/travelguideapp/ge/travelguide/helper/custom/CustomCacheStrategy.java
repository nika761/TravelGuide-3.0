package travelguideapp.ge.travelguide.helper.custom;

import android.content.Context;

import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSink;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.Util;

public class CustomCacheStrategy implements DataSource.Factory {

    private static DefaultDataSourceFactory defaultDataSourceFactory;
    private static SimpleCache simpleCache;

    private static CacheDataSink cacheDataSink;
    private static FileDataSource fileDataSource;

    private Context context;

    CustomCacheStrategy(Context context) {
        String userAgent = "TravelGuide";

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        defaultDataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, userAgent));
        simpleCache = new SimpleCache(context.getCacheDir(), new LeastRecentlyUsedCacheEvictor(100 * 1024 * 1024));
        cacheDataSink = new CacheDataSink(simpleCache, 5 * 1024 * 1024);
        fileDataSource = new FileDataSource();
    }

    @Override
    public DataSource createDataSource() {
        return new CacheDataSource(simpleCache, defaultDataSourceFactory.createDataSource(), fileDataSource, cacheDataSink, CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR, null);
    }
}
