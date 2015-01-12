package in.tkch.androidbootstrap.mortar.pathview;

import android.content.Context;
import android.util.AttributeSet;
import flow.Flow;
import flow.Path;
import flow.PathContextFactory;
import in.tkch.androidbootstrap.R;
import in.tkch.androidbootstrap.mortar.Paths.MasterDetailPath;

public class MasterPathContainerView extends FramePathContainerView {

  public MasterPathContainerView(Context context, AttributeSet attrs) {
    super(context, attrs, new MasterPathContainer(R.id.screen_switcher_tag, Path.contextFactory()));
  }

  @Override public void dispatch(Flow.Traversal traversal, final Flow.TraversalCallback callback) {

    MasterDetailPath currentMaster =
        ((MasterDetailPath) Flow.get(getContext()).getBackstack().current()).getMaster();

    MasterDetailPath newMaster = ((MasterDetailPath) traversal.destination.current()).getMaster();

    // Short circuit if the new screen has the same master.
    if (getCurrentChild() != null && newMaster.equals(currentMaster)) {
      callback.onTraversalCompleted();
    } else {
      super.dispatch(traversal, new Flow.TraversalCallback() {
        @Override public void onTraversalCompleted() {
          callback.onTraversalCompleted();
        }
      });
    }
  }

  static class MasterPathContainer extends SimplePathContainer {

    MasterPathContainer(int tagKey, PathContextFactory contextFactory) {
      super(tagKey, contextFactory);
    }

    @Override protected int getLayout(Path path) {
      MasterDetailPath mdPath = (MasterDetailPath) path;
      return super.getLayout(mdPath.getMaster());
    }
  }
}
