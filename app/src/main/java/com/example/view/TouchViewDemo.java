package com.example.view;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by      android studio
 *
 * @author :       ly
 * Date            :       2021-08-04
 * Time            :       2:16 PM
 * Version         :       1.0
 * location        :       武汉研发中心
 * 功能描述         :
 **/
public class TouchViewDemo {
    /**是否应该向子组件传递事件**/
    boolean childConsume = false;
    /**本组件是Activity**/
    boolean isActivity = true | false;
    /**本组件是ViewGroup**/
    boolean isViewGroup = true | false;
    /**本组件是View而不是ViewGroup**/
    boolean isView = true | false;
    /**子组件是View(包括ViewGroup)**/
    boolean childIsView = true | false;

    /**有无子组件或者事件源是否在直接或间接子组件上**/
    boolean hasChild = true | false;
    /**模拟子视图组件**/
    Child child = new Child();
    /**外部监听器**/
    View.OnTouchListener touchListener = null;
    View.OnLongClickListener longClickListener = null;
    View.OnClickListener clickListener = null;
    /**本类对象，非Activity**/
    View view = null;
    /**默认状态下触摸事件的逻辑处理方法，当前对象层级视图组件触摸事件处理的入口**/
    public boolean dispatchTouchEvent(MotionEvent event) {
        /**本方法的返回值**/
        boolean b = false;
        if(isActivity) {//如果本对象是Activity。//完全由onTouchEvent()决定本方法的返回值
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                onUserInteraction();
                childConsume = hasChild ? true : false;
                if(childConsume) {
                    if(child.dispatchTouchEvent(event)) {//注意子组件的dispatchTouchEvent(event)处理过程跟本方法相同
                        childConsume = true;
                        b = true;
                    }else {
                        childConsume = false;
                        b = onTouchEvent(event);
                    }
                }else {
                    b = onTouchEvent(event);
                }
            }else if(childConsume) {//即使后续事件中child.dispatchTouchEvent(event)返回false，也继续传递
                if(child.dispatchTouchEvent(event)) {
                    b = true;
                }else {
                    b = onTouchEvent(event);
                }
            }else {
                b = onTouchEvent(event);
            }
        }else if(isViewGroup) {//如果本对象是ViewGroup
            boolean intercept = false;
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                intercept = onInterceptTouchEvent(event);//不管有没有子组件，本方法都将执行
                childConsume = (hasChild && ! intercept) ? true : false;
                if(childConsume) {
                    if(child.dispatchTouchEvent(event)) {
                        childConsume = true;
                        b = true;
                    }else {
                        childConsume = false;
                        if(touchListener != null) {
                            b = touchListener.onTouch(view, event);//调用外部监听器
                        }
                        if(!b) b = onTouchEvent(event);
                    }
                }else {
                    childConsume = false;
                    if(touchListener != null) {
                        b = touchListener.onTouch(view, event);
                    }
                    if(!b) b = onTouchEvent(event);
                }
            }else if(event.getAction() == MotionEvent.ACTION_MOVE) {
                if(childConsume) {
                    intercept = onInterceptTouchEvent(event);
                    if(intercept) {
                        childConsume = false;
                        event.setAction(MotionEvent.ACTION_CANCEL);//重点，后续事件被拦截，将变为取消事件并继续传递
                        child.dispatchTouchEvent(event);
                        b = true;//此时不论child.dispatchTouchEvent(event)返回什么值，本方法都直接返回true
                    }else {
                        b = child.dispatchTouchEvent(event);//即使返回false也直接返回，区别于Activity
                    }
                }else {
                    if(touchListener != null) {
                        b = touchListener.onTouch(view, event);
                    }
                    if(!b) b = onTouchEvent(event);
                }
            }else if(event.getAction() == MotionEvent.ACTION_UP) {
                if(childConsume) {
                    intercept = onInterceptTouchEvent(event);
                    if(intercept) {
                        childConsume = false;
                        event.setAction(MotionEvent.ACTION_CANCEL);//重点，后续事件被拦截，将变为取消事件并继续传递
                        child.dispatchTouchEvent(event);
                        b = true;//此时不管child.dispatchTouchEvent(event)返回什么值，本方法都直接返回true
                    }else {
                        b = child.dispatchTouchEvent(event);//即时返回false也直接返回，区别于Activity
                    }
                }else {
                    if(touchListener != null) {
                        b = touchListener.onTouch(view, event);
                    }
                    if(!b) b = onTouchEvent(event);//注意onClick事件是在onTouchEvent()内部处理的
                }
            }
        }else if(isView) {//如果本对象是不能添加子组件的View，如：Button、EditText
            if(touchListener != null) {
                b = touchListener.onTouch(view, event);
            }
            if(!b) b = onTouchEvent(event);
        }
        return b;
    }
    /**Activity类特有，重写它，可用于在事件捕获阶段，事件到达本层容器而未进行任何其他处理之前做些事情**/
    public void onUserInteraction() {}
    /**ViewGroup类特有，其返回值表示是否拦截事件，以决定事件是否继续传递**/
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return true | false;
    }
    /**Activity默认返回false，View默认返回true。返回值会决定后续的事件传递情况。可以根据需要自定义返回值。**/
    public boolean onTouchEvent(MotionEvent event) {
        /*
         * 当Activity收到MotionEvent.ACTION_DOWN事件的冒泡返回，则会启动一个LongClick计时线程，之后若View组件树中
         * 有任何一个组件在延时之内收到MotionEvent.ACTION_UP或MotionEvent.ACTION_CANCEL事件，则计时终止。
         * 因此LongClick事件在两种状况下触发：
         *
         * a、若父级Activity收到child.dispatchTouchEvent(event)的返回值为false时；
         * b、长按某组件，MotionEvent.ACTION_UP或MotionEvent.ACTION_CANCEL事件发生在LongClick之后时。
         *
         * LongClick事件触发时，从叶子层组件向根层组件依次调用View.OnLongClickListener.onLongClick(View v)方法。
         * 该方法优先于组件本身默认的longClick相关处理方法，若返回值为false，会使组件继续调用默认的处理方法，
         * 否则不执行默认的处理方法并给View.OnClickListener.onClick(View v)一个不要执行的标识。
         * 但返回值不会影响上层组件对该方法的调用。
         */
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                /* Post本对象到LongClick计时线程 */
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                /* 终止LongClick计时线程 */
                /* 执行View.OnClickListener.onClick(View v)。若View.OnLongClickListener.onLongClick(View v)先执行，
                 * 则根据其返回值决定是否执行View.OnClickListener.onClick(View v) */
                break;
        }
        return true | false;
    }
    /**添加外部监听器。View类特有(包括ViewGroup)，注意无论调用多少次本方法，只有最后一个监听器起作用，即覆盖**/
    public void setOnTouchListener(View.OnTouchListener l) {
        touchListener = l;
    }
    /**添加外部监听器。View类特有(包括ViewGroup)，注意无论调用多少次本方法，只有最后一个监听器起作用，即覆盖**/
    public void setOnClickListener(View.OnClickListener l) {
        clickListener = l;
    }
    /**添加外部监听器。View类特有(包括ViewGroup)，注意无论调用多少次本方法，只有最后一个监听器起作用，即覆盖**/
    public void setOnLongClickListener(View.OnLongClickListener l) {
        longClickListener = l;
    }
}

class Child extends TouchViewDemo {}
