package kienpd.com.mtee.ui.user.info;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.db.StorageManager;
import kienpd.com.mtee.data.model.User;
import kienpd.com.mtee.ui.base.BaseDialog;
import kienpd.com.mtee.utils.TextUtil;
import kienpd.com.mtee.utils.TimeUtil;

public class InfoFragment extends BaseDialog implements InfoMvpView, View.OnClickListener {

    InfoMvpPresenter<InfoMvpView> mPresenter;

    public static final String TAG = "INFO_FRAGMENT";
    public static final String EXTRAS_USER = "extras_detail";

    @BindView(R.id.image_avatar)
    ImageView mImageAvatar;

    @BindView(R.id.image_name)
    ImageView mImageName;

    @BindView(R.id.image_phone)
    ImageView mImagePhone;

    @BindView(R.id.image_mail)
    ImageView mImageMail;

    @BindView(R.id.image_gender)
    ImageView mImageGender;

    @BindView(R.id.image_birthday)
    ImageView mImageBirthday;

    @BindView(R.id.edit_name)
    EditText mEditName;

    @BindView(R.id.edit_mail)
    TextView mEditMail;

    @BindView(R.id.edit_phone)
    EditText mEditPhone;

    @BindView(R.id.text_birthday)
    TextView mTextBirthday;

    @BindView(R.id.text_gender)
    TextView mTextGender;

    @BindView(R.id.layout_save)
    LinearLayout mLayoutSave;

    @BindView(R.id.text_title_center)
    TextView mTextCenter;

    @BindView(R.id.image_back)
    ImageView mImageBack;

    private String mJsonUser;
    private Long mBirthday;
    private Integer mUserId;

    public static InfoFragment newInstance(String jsonUser) {

        InfoFragment f = new InfoFragment();
        Bundle args = new Bundle();
        args.putString(EXTRAS_USER, jsonUser);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        setUnBinder(ButterKnife.bind(this, view));
        mPresenter = new InfoPresenter<>();
        mPresenter.onAttach(this);

        return view;
    }

    @Override
    protected void setUp(View view) {
        mImageBirthday.setColorFilter(getResources().getColor(R.color.color_item_un_select));
        mImageName.setColorFilter(getResources().getColor(R.color.color_item_un_select));
        mImagePhone.setColorFilter(getResources().getColor(R.color.color_item_un_select));
        mImageMail.setColorFilter(getResources().getColor(R.color.color_item_un_select));
        mImageGender.setColorFilter(getResources().getColor(R.color.color_item_un_select));
        mImageBirthday.setColorFilter(getResources().getColor(R.color.color_item_un_select));

        mJsonUser = getArguments().getString(EXTRAS_USER);
        if (mJsonUser != null && !TextUtil.isEmpty(mJsonUser)) {
            Gson gson = new Gson();
            User user = gson.fromJson(mJsonUser, User.class);
            if (user != null) {
                displayInfo(user);
            }
        }

        mTextCenter.setText("Thông tin cá nhân");
        mLayoutSave.setOnClickListener(this);
        mTextBirthday.setOnClickListener(this);
        mImageBack.setOnClickListener(this);
    }

    @Override
    public void onBackButtonPressed() {
        dismissDialog(TAG);
    }

    @Override
    public void displayInfo(User user) {
        if (user != null) {

            //Id
            mUserId = user.getId();

            //Avatar
            Glide.with(getBaseActivity())
                    .load(user.getPicture())
                    .apply(RequestOptions.circleCropTransform())
                    .into(mImageAvatar);

            //Email
            mEditMail.setText(user.getEmail());

            //Name
            String name = user.getName();
            if (!TextUtil.isEmpty(name)) {
                mEditName.setText(name);
            } else {
                mEditName.setText(TextUtil.EMPTY_STRING);
            }

            //Phone
            Integer phone = user.getPhone();
            if (phone != null && phone != 0) {
                mEditPhone.setText("0" + phone);
            } else {
                mEditPhone.setText(TextUtil.EMPTY_STRING);
            }

            //Birthday
            Long birthday = user.getBirthday();
            mBirthday = birthday;
            if (birthday != null) {
                String sBirthday = TimeUtil.getStringDateFromMiliseconds(birthday);
                mTextBirthday.setText(sBirthday);
            } else {
                mTextBirthday.setText(TextUtil.EMPTY_STRING);
            }

            //Gender
            String gender = user.getGender();
            if (!TextUtil.isEmpty(gender)) {
                mTextGender.setText(gender);
            } else {
                mTextGender.setText(TextUtil.EMPTY_STRING);
            }

        }
    }

    @Override
    public void updateData(Boolean isUpdate) {
        mEditMail.clearFocus();
        mEditPhone.clearFocus();
        mEditName.clearFocus();
        if (isUpdate) {
            getBaseActivity().showMessage("Đã cập nhật");
        } else {
            getBaseActivity().showMessage("Cập nhật thất bại");
        }
    }

    @Override
    public void saveUserDataCache(User user) {
        if (user != null) {
            StorageManager.saveUserDetails(getBaseActivity(), user);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_save:
                long milliseconds = 0;
                SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date d = f.parse(mTextBirthday.getText().toString());
                    milliseconds = d.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mPresenter.updateUserData(mJsonUser, mUserId, mEditName.getText().toString(), Integer.valueOf(mEditPhone.getText().toString()), milliseconds, mTextGender.getText().toString());
                break;
            case R.id.text_birthday:
                int year = 2018, month = 11, day = 1;
                if (mBirthday != null) {
                    Calendar c = Calendar.getInstance();
                    c.setTimeInMillis(mBirthday);
                    year = c.get(Calendar.YEAR);
                    month = c.get(Calendar.MONTH);
                    day = c.get(Calendar.DAY_OF_MONTH);
                }
                DatePickerDialog datePickerDialog = new DatePickerDialog(getBaseActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                mTextBirthday.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        }, year, month, day);
                datePickerDialog.show();
                break;
            case R.id.image_back:
                dismissDialog(TAG);
                break;
            default:
                break;
        }
    }
}
