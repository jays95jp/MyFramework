package com.kevadiyakrunalk.myframework.viewmodels;

import android.app.DatePickerDialog;
import android.content.Context;
import android.databinding.Bindable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.kevadiyakrunalk.commonutils.common.Logs;
import com.kevadiyakrunalk.mvvmarchitecture.common.BaseViewModel;
import com.kevadiyakrunalk.myframework.BR;
import com.kevadiyakrunalk.myframework.databinding.FragmentValidationBinding;
import com.kevadiyakrunalk.myframework.other.validation.CustomEmailDomainValidator;
import com.kevadiyakrunalk.myframework.other.validation.ExternalApiEmailValidator;
import com.kevadiyakrunalk.rxvalidation.RxValidationResult;
import com.kevadiyakrunalk.rxvalidation.RxValidator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;

public class ValidationFragmentViewModel extends BaseViewModel {
    private Context context;
    private Logs logs;
    private boolean validationEnagle;
    private FragmentValidationBinding binding;

    private static final String dateFormat = "dd-MM-yyyy";
    private static final SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

    public ValidationFragmentViewModel(Context context, Logs logs) {
        this.context = context;
        this.logs = logs;
        validationEnagle = false;
    }

    public void  initBinder(FragmentValidationBinding binding) {
        this.binding = binding;
    }

    @Bindable
    public boolean isValidationEnagle() {
        return validationEnagle;
    }

    public void setValidationEnagle(boolean validationEnagle) {
        this.validationEnagle = validationEnagle;
        notifyPropertyChanged(BR.validationEnagle);
    }

    public void initValue() {
        RxValidator.createFor(binding.email)
                .nonEmpty()
                .email()
                .with(new CustomEmailDomainValidator())
                .with(new ExternalApiEmailValidator())
                .onValueChanged()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RxValidationResult<EditText>>() {
                    @Override public void call(RxValidationResult<EditText> result) {
                        binding.ilEmail.setError(result.isProper() ? null : result.getMessage());
                        logs.error("VALIDATION", "Validation result " + result.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override public void call(Throwable throwable) {
                        logs.error("VALIDATION", "Validation error" + throwable);
                    }
                });

        RxValidator.createFor(binding.password)
                .nonEmpty()
                .minLength(5, "Min length is 5")
                .onFocusChanged()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RxValidationResult<EditText>>() {
                    @Override public void call(RxValidationResult<EditText> result) {
                        binding.ilPassword.setError(result.isProper() ? null : result.getMessage());
                        logs.error("VALIDATION", "Validation result " + result.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override public void call(Throwable throwable) {
                        logs.error("VALIDATION", "Validation error" + throwable);
                    }
                });

        RxValidator.createFor(binding.confirmPassword)
                .sameAs(binding.password, "Password does not match")
                .onFocusChanged()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RxValidationResult<EditText>>() {
                    @Override public void call(RxValidationResult<EditText> result) {
                        binding.ilConfirmPassword.setError(result.isProper() ? null : result.getMessage());
                        logs.error("VALIDATION", "Validation result " + result.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override public void call(Throwable throwable) {
                        logs.error("VALIDATION", "Validation error" + throwable);
                    }
                });

        RxValidator.createFor(binding.birthday)
                .age("You have to be 18y old", 18, sdf)
                .onValueChanged()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RxValidationResult<EditText>>() {
                    @Override public void call(RxValidationResult<EditText> result) {
                        binding.ilBirthday.setError(result.isProper() ? null : result.getMessage());
                        logs.error("VALIDATION", "Validation result " + result.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override public void call(Throwable throwable) {
                        logs.error("VALIDATION", "Validation error" + throwable);
                    }
                });

        RxValidator.createFor(binding.ip4Address)
                .ip4("Invalid IP4 format")
                .onValueChanged()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RxValidationResult<EditText>>() {
                    @Override public void call(RxValidationResult<EditText> result) {
                        binding.ilIp4Address.setError(result.isProper() ? null : result.getMessage());
                        logs.error("VALIDATION", "Validation result " + result.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override public void call(Throwable throwable) {
                        logs.error("VALIDATION", "Validation error" + throwable);
                    }
                });

        Observable.combineLatest(
                RxValidator.createFor(binding.email)
                        .nonEmpty()
                        .email()
                        .with(new CustomEmailDomainValidator())
                        .with(new ExternalApiEmailValidator())
                        .onValueChanged()
                        .toObservable(),
                RxValidator.createFor(binding.birthday)
                        .age("You have to be 18y old", 18, sdf)
                        .onValueChanged()
                        .toObservable(),
                new Func2<RxValidationResult<EditText>, RxValidationResult<EditText>, Boolean>() {
                    @Override
                    public Boolean call(RxValidationResult<EditText> o1, RxValidationResult<EditText> o2) {
                        return (o1.isProper() && o2.isProper());
                    }
                }).
                subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        setValidationEnagle(aBoolean);
                    }
                });
    }

    public void onBirthday(View view) {
        showDatePicker((EditText) view);
    }

    private void showDatePicker(final EditText birthday) {
        DatePickerDialog dpd =
                new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Date selectedDate = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
                        birthday.setText(sdf.format(selectedDate));
                    }
                }, 2016, 0, 1);
        dpd.show();
    }
}
