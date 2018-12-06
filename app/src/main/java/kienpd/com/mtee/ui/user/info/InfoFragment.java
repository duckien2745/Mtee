package kienpd.com.mtee.ui.user.info;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
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

    @BindView(R.id.edit_name)
    EditText mEditName;

    @BindView(R.id.edit_mail)
    EditText mEditMail;

    @BindView(R.id.edit_phone)
    EditText mEditPhone;

    @BindView(R.id.text_birthday)
    TextView mTextBirthday;

    @BindView(R.id.text_gender)
    TextView mTextGender;

    @BindView(R.id.layout_save)
    LinearLayout mLayoutSave;

    private String mJsonUser;
    private Long mBirthday;

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
        mJsonUser = getArguments().getString(EXTRAS_USER);
        if (mJsonUser != null && !TextUtil.isEmpty(mJsonUser)) {
            Gson gson = new Gson();
            User user = gson.fromJson(mJsonUser, User.class);
            if (user != null) {
                displayInfo(user);
            }
        }

        mLayoutSave.setOnClickListener(this);
        mTextBirthday.setOnClickListener(this);
    }

    @Override
    public void displayInfo(User user) {
        if (user != null) {

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
                mEditPhone.setText(phone + TextUtil.EMPTY_STRING);
            } else {
                mEditPhone.setText(TextUtil.EMPTY_STRING);
            }

            //Birthday
            Long birthday = user.getBirthday();
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_save:
                break;
            case R.id.text_birthday:
                if (mBirthday != null) {
                    Calendar c = Calendar.getInstance();
                    c.setTimeInMillis(mBirthday);
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getBaseActivity(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    mTextBirthday.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, year, month, day);
                    datePickerDialog.show();
                }
                break;
            default:
                break;
        }
    }
}
