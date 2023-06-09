package com.android.expandableview

import CustomItemAnimator
import android.animation.LayoutTransition
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.android.expandableview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var expand = false
        val list = listOf<SampleData>(
            SampleData("장점", "고민이 가지고 있는 선택지를 나열해보고, "),
            SampleData("단점", "그 선택지가 어떤 장점과 단점을 가지고 있는지 생각할 수 있는 시간을 가질 수 있어요.고민이 가지고 있는 선택지를 나열해보고,"),
            SampleData("생각하기", "고민이 가지고 있는 선택지를 나열해보고, 그 선택지가 어떤 장점과 단점을 가지고 있는지 생각할 수 있는 시간을 가질 수 있어요.고민이 가지고 있는 선택지를 나열해보고, 그 선택지가 어떤 장점과 단점을 가지고 있는지 생각할 수 있는 시간을 가질 수 있어요.고민이 가지고 있는 선택지를 나열해보고, 그 선택지가 어떤 장점과 단점을 가지고 있는지 생각할 수 있는 시간을 가질 수 있어요.")
        )
        val expandDrawable = ContextCompat.getDrawable(this, R.drawable.gradient_expand)
        val collapseDrawable = ContextCompat.getDrawable(this, R.drawable.gradient_collapse)
        // 현재 이슈
        // radient의 centerX, centerY값이 view의 visible gone 상태에
        // 따라서 불규칙적으로 변하고 있음 그렇기 떄문에 이후 벡터로 할건지 아니면
        // 근본적인 해결책을 찾아낼것인지 고민해봐야함 일단은 view의 상태 변화에도
        // 약간 gradient의 차이가 발생하더라도 drawalbe을 교체하지 않는 이상태로 두겠음
        // 혹시 리싸이클러뷰에서 적용하면 다르게 작동할지도 모름
        val adapter = ExpandAdapter()
        binding.rcv.adapter = adapter
        //binding.rcv.itemAnimator = CustomItemAnimator()

        adapter.submitList(list)
    }

    fun toggleLayout(isExpanded: Boolean, view: View, layoutExpand: ConstraintLayout): Boolean {
        // 토글 레이아웃을 위한 애니메이션
        // 열심히 만들어놨는데 실기기에서 깜빡임 이슈가 있어서
        // constraintLayout에 android:animateLayoutChanges="true" 하나만 추가하면 됨
        // 하지만 리싸이클러뷰와 함께 사용할 경우 이슈가 생겨서 다시 커스텀 애니메이션 사용
        // 높이 측정을 정밀하게 해주는 것으로 해결
        ExpandAnimation.toggleArrow(view, isExpanded)
        if (isExpanded) {
            ExpandAnimation.expand(layoutExpand)
        } else {
            ExpandAnimation.collapse(layoutExpand)
        }
        return isExpanded
    }
}