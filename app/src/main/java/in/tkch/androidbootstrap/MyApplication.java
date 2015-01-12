package in.tkch.androidbootstrap;

import android.app.Application;

import com.google.gson.Gson;

import javax.annotation.Nullable;

import flow.Backstack;
import in.tkch.androidbootstrap.mortar.GsonParceler;
import in.tkch.androidbootstrap.mortar.util.FlowBundler;
import lombok.Getter;
import mortar.Mortar;
import mortar.MortarScope;
import mortar.dagger2support.Dagger2;

/**
 * Created by takuji on 15/01/12.
 */
public class MyApplication extends Application {
    @Getter
    private final FlowBundler flowBundler = new FlowBundler(new GsonParceler(new Gson())) {
        @Override protected Backstack getColdStartBackstack(@Nullable Backstack restoredBackstack) {
            //TODO initial screen
            return restoredBackstack == null ? Backstack.single(null) : restoredBackstack;
        }
    };

    @Getter
    private MortarScope rootScope;

    @Override
    public void onCreate() {
        super.onCreate();
        rootScope = Mortar.createRootScope(Dagger2.buildComponent(GlobalScope.Component.class));
    }
}
