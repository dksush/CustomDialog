package com.example.customdialog;


import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.example.customdialog.databinding.PopupListBinding;


public class CustomDialog extends DialogFragment {  // 왜 DialogFragment 인가? 구글이 왠만하면 이거쓰라고 추천한다. 왜인지는 모르겟다.

    /*
    다이어로그 생성자 (라사 등 다른 생성자와는 조금 다르다) : 우선 자신을 객체로 선언. : 아니지 리사처럼 해도 되겟네.
    1. 외부에서 사용시 파라미터로 해당 인터페이스를 넣어준다.    인터페이스를 등록해주면,  dialogFragment.OnMenuClick(mClickListener); 로 1,2,3이 차례로 발동된다.
    2. 해당 인터페이스 파라미터를 통해 외부용 메소드(OnMenuClick) 가 실행되고, 최종적으로 인터페이스의 메소드를 불러온다.
    3. UI 초기화는 onCreateView 에 선언한다.
    4. 클릭 이벤트를 등록해준다.
    5. 클릭 이벤트 세부 내용은, 외부 클래스에 원하는대로 정의한다.
    */
    public static CustomDialog dialog(ClickListener mClickListener) {
        CustomDialog dialogFragment = new CustomDialog(); // 자신을 객체화.
        dialogFragment.OnMenuClick(mClickListener);

        return dialogFragment;
    }

    // 클릭 리스너 인터페이스.
    public interface ClickListener{
        void hungry(Dialog dialog);
        void sleep(Dialog dialog);
    }
    private ClickListener mClickListener = null; // 여기 내부에서 위의 인터페이스를 받을 놈. 2
    public void OnMenuClick(ClickListener clickListener){ // 외부에서 쓸놈. 1
        this.mClickListener = clickListener;
    }


    private PopupListBinding mBinding;


    @Override // 유아이 및 기능 초기화.
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.popup_list, container, false);
        mBinding.popupTopBtn.setText("졸려");
        mBinding.popupBottomBtn.setText("배고파");


        mBinding.layoutRoot.setOnClickListener(v->getDialog().dismiss()); // 다이얼로그 외부 누르면 끈다.(람다)

        // 클릭 이벤트만 등록. 세부 동작 내용은 이 다이얼로그를 사용하는 외부클래스에 선언.
        mBinding.popupTopBtn.setOnClickListener(v -> {
            if (mClickListener != null) {
                mClickListener.hungry(getDialog());// 이 다이얼로그를 받아쓸 곳에 넘기나.
            }});


        mBinding.popupBottomBtn.setOnClickListener(v -> {
            if (mClickListener != null) {
                mClickListener.sleep(getDialog());
            }});





        return mBinding.getRoot();
    }


    // 다이얼로그의 세부 속성값을 지정할 수 있다.
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        /*
          뷰 내부의 텍스트는 setText 등으로 쉽게 변경 가능.
          but 레이아웃의 속성값을 변경할 때에는 LayoutParams 이 필요.
        */
        // final RelativeLayout root = new RelativeLayout(getActivity());
        //root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final Dialog dialog = new Dialog(getActivity());

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE); // 타이틀바 없애기.
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND); // 다이얼로그 호출시 배경이 검정색이 되는 걸 막는다.
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT)); // 배경 투명하게. 안하면 이상한 박스같은게 원래 화면을 가린다. 그리고 xml에 별도의 어두운 투명바탕을 설정했다.
        dialog.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN); // 풀스크린
        // dialog.setContentView(root);
        dialog.setContentView(R.layout.popup_list);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return dialog;
    }
}