package in.tkch.androidbootstrap.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import in.tkch.androidbootstrap.MyApplication;
import lombok.Getter;
import mortar.Mortar;
import mortar.MortarActivityScope;
import mortar.MortarScope;
import mortar.dagger2support.Dagger2;

/**
 * Created by takuji on 15/01/12.
 */
public abstract class BaseActivity extends ActionBarActivity {
    @Getter
    private MyApplication app;

    @Getter
    private MortarActivityScope activityScope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (MyApplication) getApplication();

        activityScope = findOrCreateActivityScope();

        if (activityScope == null) {
            throw new IllegalStateException("Activity scope did not created!");
        }

        onInject();
        activityScope.onCreate(savedInstanceState);

    }

    protected abstract String getScopeName();
    protected abstract Class<?> getComponentClass();
    protected abstract void onInject();

    protected Class<?>[] getDependencies() {
        return null;
    }

    protected MortarActivityScope findOrCreateActivityScope() {
        MortarScope parentScope = getApp().getRootScope();
        String scopeName = getScopeName();
        MortarActivityScope activityScope = (MortarActivityScope) parentScope.findChild(scopeName);
        if (activityScope == null) {
            Class<?>[] dependencies = getDependencies();
            Object activityGraph = dependencies == null ? Dagger2.buildComponent(getComponentClass()) : Dagger2.buildComponent(getComponentClass(), (Object[])dependencies);
            activityScope = Mortar.createActivityScope(parentScope, scopeName, activityGraph);
        }
        return activityScope;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        activityScope.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing() && activityScope != null) {
            MortarScope parentScope = getApp().getRootScope();
            parentScope.destroyChild(activityScope);
            activityScope = null;
        }
    }

    @Override
    public Object getSystemService(String name) {
        if (Mortar.isScopeSystemService(name)) {
            return activityScope;
        }
        return super.getSystemService(name);
    }
}
