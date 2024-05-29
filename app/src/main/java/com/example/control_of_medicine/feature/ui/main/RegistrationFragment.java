package com.example.control_of_medicine.feature.ui.main;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.control_of_medicine.Keys;
import com.example.control_of_medicine.R;
import com.example.control_of_medicine.data.UserRepository;
import com.example.control_of_medicine.databinding.FragmentRegistrationBinding;
import com.example.control_of_medicine.feature.presentation.RegistrationViewModel;
import com.example.control_of_medicine.feature.ui.main_pages.MainPagesActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.GoogleAuthProvider;

public class RegistrationFragment extends Fragment {

    private Keys keys;
    private final String OAUTH_KEY = Keys.getOauthKey();

    private FragmentRegistrationBinding binding;

    private RegistrationViewModel mViewModel;

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    private final ActivityResultLauncher<Intent> googleLoginLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> handleGoogleLoginResult(result)
    );

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(getLayoutInflater());

        binding.signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment();
            }
        });

        binding.loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginWithEmail();
            }
        });

        binding.loginGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginWithGoogle();
            }
        });

        FirebaseAuth.getInstance().getCurrentUser();

        return binding.getRoot();
    }

    private void loginWithGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail() // optional
                .build();
        GoogleSignInClient client = GoogleSignIn.getClient(requireActivity().getApplicationContext(), gso);
        Intent intent = client.getSignInIntent();
        googleLoginLauncher.launch(intent);
    }

    private void handleGoogleLoginResult(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            GoogleSignIn.getSignedInAccountFromIntent(result.getData())
                    .addOnSuccessListener(googleSignInAccount -> {
                        String tokenId = googleSignInAccount.getIdToken();
                        firebaseAuthWithGoogle(tokenId);
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(requireActivity().getApplicationContext(), "Google error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(requireActivity().getApplicationContext(), "Google error", Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(String tokenId) {
        AuthCredential credential = GoogleAuthProvider.getCredential(tokenId, OAUTH_KEY);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnSuccessListener(authResult -> {
                    Toast.makeText(requireActivity().getApplicationContext(), "Google login success: " + authResult.getUser().getUid(), Toast.LENGTH_SHORT).show();
                    RegistrationFragment.this.startActivity(MainPagesActivity
                            .creatIntent(requireActivity().getApplicationContext()));
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(requireActivity().getApplicationContext(), "Google login error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void loginWithEmail() {
        String email = binding.email.getText().toString();
        String password = binding.password.getText().toString();
        if (validateFields(email, password)) {
            signupFirebase(email, password);
        }
    }

    public Boolean validateFields(String email, String password) {
        if (!email.contains("@")) {
            binding.email.setError("Wrong email");
            return false;
        }
        if (password.isEmpty()) {
            binding.password.setError("Password should not be empty");
            return false;
        }
        return true;
    }

    private void signupFirebase(String email, String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    Toast.makeText(requireActivity().getApplicationContext(), "Login success: "
                            + authResult.getUser().getUid(), Toast.LENGTH_SHORT).show();

                    RegistrationFragment.this.startActivity(MainPagesActivity
                            .creatIntent(requireActivity().getApplicationContext()));

                    //Здесь написать логику добавления документа юзера с коллекциями (таблетки, время) в Firestore, сделать отдельный класс и метод!!!

                    UserRepository.createUser(authResult.getUser());

        }).addOnFailureListener(e -> {
            if (e instanceof FirebaseAuthUserCollisionException) {
                Toast.makeText(requireActivity().getApplicationContext(),
                        "У вас уже есть аккаунт", Toast.LENGTH_SHORT).show();
                setFragment();
            } else {
                Toast.makeText(requireActivity().getApplicationContext(),
                        "SignUp error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
    }

    private void setFragment() {
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_reg_sign,
                SignFragment.newInstance(), null).addToBackStack("Go back").commit();
    }

}