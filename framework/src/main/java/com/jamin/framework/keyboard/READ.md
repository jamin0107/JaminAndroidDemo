#使用说明
###1. mainfest.xml注册
```
Android Manifest: android:windowSoftInputMode="adjustResize"

```


###2. 监听键盘弹起,收回
RootLayout注册KeyBoard监听
```
RelativeLayout mainLayout = findViewById(R.layout.main_layout); // You must use the layout root
InputMethodManager im = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
SoftKeyboard softKeyboard = new SoftKeyboard(mainLayout, im);
softKeyboard.setSoftKeyboardCallback(new SoftKeyboard.SoftKeyboardChanged()
{

	@Override
	public void onSoftKeyboardHide()
	{
		// Code here
	}

	@Override
	public void onSoftKeyboardShow()
	{
		// Code here
	}
});

```

###3. 快速打开,收回键盘.

```
softKeyboard.openSoftKeyboard();
softKeyboard.closeSoftKeyboard();

```
###4. 销毁
```
@Override
public void onDestroy()
{
    super.onDestroy();
    softKeyboard.unRegisterSoftKeyboardCallback();
}
```
