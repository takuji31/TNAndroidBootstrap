package in.tkch.androidbootstrap;

import javax.inject.Singleton;

import in.tkch.androidbootstrap.activity.BaseActivity;
import in.tkch.androidbootstrap.activity.MainActivity;

/**
 * Created by takuji on 15/01/12.
 */
public final class GlobalScope {
    @dagger.Component
    @Singleton
    public static interface Component {
        void inject(MyApplication t);
        void inject(MainActivity t);
        void inject(BaseActivity t);
    }
    private GlobalScope() {

    }
}
