package in.tkch.androidbootstrap.mortar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import javax.annotation.Nonnull;

import flow.Flow;
import flow.Path;
import flow.PathContainerView;
import in.tkch.androidbootstrap.R;
import in.tkch.androidbootstrap.mortar.Paths;
import in.tkch.androidbootstrap.mortar.pathview.FramePathContainerView;
import in.tkch.androidbootstrap.mortar.pathview.HandlesBack;
import in.tkch.androidbootstrap.mortar.pathview.HandlesUp;
import in.tkch.androidbootstrap.mortar.pathview.UpAndBack;

/**
 * This view is shown only in landscape orientation on tablets. See
 * the explanation in {@link com.example.flow.MainActivity#onCreate}.
 */
public class TabletMasterDetailRoot extends LinearLayout
    implements HandlesBack, HandlesUp, PathContainerView {
  private FramePathContainerView masterContainer;
  private FramePathContainerView detailContainer;

  private boolean disabled;

  public TabletMasterDetailRoot(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override public boolean dispatchTouchEvent(@Nonnull MotionEvent ev) {
    return !disabled && super.dispatchTouchEvent(ev);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();

    masterContainer = (FramePathContainerView) findViewById(R.id.master);
    detailContainer = (FramePathContainerView) findViewById(R.id.detail);
  }

  @Override public ViewGroup getCurrentChild() {
    Paths.MasterDetailPath showing = Path.get(getContext());
    return showing.isMaster() ? masterContainer.getCurrentChild()
        : detailContainer.getCurrentChild();
  }

  @Override public ViewGroup getContainerView() {
    return this;
  }

  @Override public void dispatch(Flow.Traversal traversal, Flow.TraversalCallback callback) {

    class CountdownCallback implements Flow.TraversalCallback {
      final Flow.TraversalCallback wrapped;
      int countDown = 2;

      CountdownCallback(Flow.TraversalCallback wrapped) {
        this.wrapped = wrapped;
      }

      @Override public void onTraversalCompleted() {
        countDown--;
        if (countDown == 0) {
          disabled = false;
          wrapped.onTraversalCompleted();
          ((IsMasterView) masterContainer.getCurrentChild()).updateSelection();
        }
      }
    }

    disabled = true;
    callback = new CountdownCallback(callback);
    detailContainer.dispatch(traversal, callback);
    masterContainer.dispatch(traversal, callback);
  }

  @Override public boolean onUpPressed() {
    return UpAndBack.onUpPressed(detailContainer);
  }

  @Override public boolean onBackPressed() {
    return UpAndBack.onBackPressed(detailContainer);
  }
}
