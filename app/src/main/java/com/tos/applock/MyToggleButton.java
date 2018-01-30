package com.tos.applock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.tos.blepdemo.R;

public class MyToggleButton extends View implements OnClickListener{

	/**
	 * 鍋氫负鑳屾櫙鐨勫浘鐗�
	 */
	private Bitmap backgroundBitmap;
	/**
	 * 鍙互婊戝姩鐨勫浘鐗�
	 */
	private Bitmap slideBtn;
	private Paint paint;
	/**
	 * 婊戝姩鎸夐挳鐨勫乏杈瑰眾
	 */
	private float slideBtn_left;

	/**
	 * 鍦ㄤ唬鐮侀噷闈㈠垱寤哄璞＄殑鏃跺�欙紝浣跨敤姝ゆ瀯閫犳柟娉�
	 */
	public MyToggleButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 鍦ㄥ竷灞�鏂囦欢涓０鍚嶇殑view锛屽垱寤烘椂鐢辩郴缁熻嚜鍔ㄨ皟鐢ㄣ��
	 * @param context	涓婁笅鏂囧璞�
	 * @param attrs		灞炴�ч泦
	 */
	public MyToggleButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		initView();
	}
	
	/**
	 * 鍒濆鍖�
	 */
	private void initView() {
		
		//鍒濆鍖栧浘鐗�
		backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
		slideBtn = BitmapFactory.decodeResource(getResources(), R.drawable.slide_button);
		
		
		//鍒濆鍖� 鐢荤瑪
		paint = new Paint();
		paint.setAntiAlias(true); // 鎵撳紑鎶楃煩榻�
		
		
		//娣诲姞onclick浜嬩欢鐩戝惉
		setOnClickListener(this);
	}

	/*
	 * view 瀵硅薄鏄剧ず鐨勫睆骞曚笂锛屾湁鍑犱釜閲嶈姝ラ锛�
	 * 1銆佹瀯閫犳柟娉� 鍒涘缓 瀵硅薄銆�
	 * 2銆佹祴閲弙iew鐨勫ぇ灏忋��	onMeasure(int,int);
	 * 3銆佺‘瀹歷iew鐨勪綅缃� 锛寁iew鑷韩鏈変竴浜涘缓璁潈锛屽喅瀹氭潈鍦� 鐖秜iew鎵嬩腑銆�  onLayout();
	 * 4銆佺粯鍒� view 鐨勫唴瀹� 銆� onDraw(Canvas)
	 */
	
	@Override
	/**
	 * 娴嬮噺灏哄鏃剁殑鍥炶皟鏂规硶 
	 */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		/**
		 * 璁剧疆褰撳墠view鐨勫ぇ灏�
		 * width  :view鐨勫搴�
		 * height :view鐨勯珮搴�   锛堝崟浣嶏細鍍忕礌锛�
		 */
		setMeasuredDimension(backgroundBitmap.getWidth(),backgroundBitmap.getHeight());
	}

	//纭畾浣嶇疆鐨勬椂鍊欒皟鐢ㄦ鏂规硶
	//鑷畾涔塿iew鐨勬椂鍊欙紝浣滅敤涓嶅ぇ
//	@Override
//	protected void onLayout(boolean changed, int left, int top, int right,
//			int bottom) {
//		super.onLayout(changed, left, top, right, bottom);
//	}
	
	/**
	 * 褰撳墠寮�鍏崇殑鐘舵��
	 *  true 涓哄紑
	 */
	private boolean currState = false;
	
	@Override
	/**
	 * 缁樺埗褰撳墠view鐨勫唴瀹�
	 */
	protected void onDraw(Canvas canvas) {
//		super.onDraw(canvas);
		
		// 缁樺埗 鑳屾櫙
		/*
		 * backgroundBitmap	瑕佺粯鍒剁殑鍥剧墖
		 * left	鍥剧墖鐨勫乏杈瑰眾
		 * top	鍥剧墖鐨勪笂杈瑰眾
		 * paint 缁樺埗鍥剧墖瑕佷娇鐢ㄧ殑鐢荤瑪
		 */
		canvas.drawBitmap(backgroundBitmap, 0, 0, paint);
		
		//缁樺埗 鍙粦鍔ㄧ殑鎸夐挳
		canvas.drawBitmap(slideBtn, slideBtn_left, 0, paint);
	}

	/**
	 * 鍒ゆ柇鏄惁鍙戠敓鎷栧姩锛�
	 * 濡傛灉鎷栧姩浜嗭紝灏变笉鍐嶅搷搴� onclick 浜嬩欢
	 * 
	 */
	private boolean isDrag = false;
	@Override
	/**
	 * onclick 浜嬩欢鍦╒iew.onTouchEvent 涓瑙ｆ瀽銆�
	 * 绯荤粺瀵筼nclick 浜嬩欢鐨勮В鏋愶紝杩囦簬绠�闄嬶紝鍙鏈塪own 浜嬩欢  up 浜嬩欢锛岀郴缁熷嵆璁や负 鍙戠敓浜哻lick 浜嬩欢
	 * 
	 */
	public void onClick(View v) {
		/*
		 * 濡傛灉娌℃湁鎷栧姩锛屾墠鎵ц鏀瑰彉鐘舵�佺殑鍔ㄤ綔
		 */
		if(!isDrag){
			currState = !currState;
			flushState();
		}
	}


	
	/**
	 * down 浜嬩欢鏃剁殑x鍊�
	 */
	private int firstX;
	/**
	 * touch 浜嬩欢鐨勪笂涓�涓獂鍊�
	 */
	private int lastX;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			firstX = lastX =(int) event.getX();
			isDrag = false;
			
			break;
		case MotionEvent.ACTION_MOVE:
			
			//鍒ゆ柇鏄惁鍙戠敓鎷栧姩
			if(Math.abs(event.getX()-firstX)>5){
				isDrag = true;
			}
			
			//璁＄畻 鎵嬫寚鍦ㄥ睆骞曚笂绉诲姩鐨勮窛绂�
			int dis = (int) (event.getX() - lastX);
			
			//灏嗘湰娆＄殑浣嶇疆 璁剧疆缁檒astX
			lastX = (int) event.getX();
			
			//鏍规嵁鎵嬫寚绉诲姩鐨勮窛绂伙紝鏀瑰彉slideBtn_left 鐨勫��
			slideBtn_left = slideBtn_left+dis;
			break;
		case MotionEvent.ACTION_UP:
			
			//鍦ㄥ彂鐢熸嫋鍔ㄧ殑鎯呭喌涓嬶紝鏍规嵁鏈�鍚庣殑浣嶇疆锛屽垽鏂綋鍓嶅紑鍏崇殑鐘舵��
			if (isDrag) {

				int maxLeft = backgroundBitmap.getWidth() - slideBtn.getWidth(); // slideBtn
																					// 宸﹁竟灞婃渶澶у��
				/*
				 * 鏍规嵁 slideBtn_left 鍒ゆ柇锛屽綋鍓嶅簲鏄粈涔堢姸鎬�
				 */
				if (slideBtn_left > maxLeft / 2) { // 姝ゆ椂搴斾负 鎵撳紑鐨勭姸鎬�
					currState = true;
				} else {
					currState = false;
				}

				flushState();
			}
			break;
		}
		
		flushView();
		
		return true; 
	}

	/**
	 * 鍒锋柊褰撳墠鐘舵��
	 */
	private void flushState() {
		if(currState){
			slideBtn_left = backgroundBitmap.getWidth()-slideBtn.getWidth();
		}else{
			slideBtn_left = 0;
		}
		
		flushView(); 
	}
	
	/**
	 * 鍒锋柊褰撳墠瑙嗗姏
	 */
	private void flushView() {
		/*
		 * 瀵� slideBtn_left  鐨勫�艰繘琛屽垽鏂� 锛岀‘淇濆叾鍦ㄥ悎鐞嗙殑浣嶇疆 鍗�       0<=slideBtn_left <=  maxLeft
		 * 
		 */
		
		int maxLeft = backgroundBitmap.getWidth()-slideBtn.getWidth();	//	slideBtn 宸﹁竟灞婃渶澶у��
		
		//纭繚 slideBtn_left >= 0
		slideBtn_left = (slideBtn_left>0)?slideBtn_left:0;
		
		//纭繚 slideBtn_left <=maxLeft
		slideBtn_left = (slideBtn_left<maxLeft)?slideBtn_left:maxLeft;
		
		/*
		 * 鍒锋柊褰撳墠瑙嗗浘  瀵艰嚧 鎵цonDraw鎵ц
		 */
		invalidate();
	}

}
