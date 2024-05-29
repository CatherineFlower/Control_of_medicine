package com.example.control_of_medicine.feature.ui.main;

import static android.app.Activity.RESULT_OK;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.control_of_medicine.Keys;
import com.example.control_of_medicine.R;
import com.example.control_of_medicine.databinding.FragmentSignBinding;
import com.example.control_of_medicine.feature.presentation.RegistrationViewModel;
import com.example.control_of_medicine.feature.presentation.SignViewModel;
import com.example.control_of_medicine.feature.ui.main_pages.MainPagesActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignFragment extends Fragment {

    private final String OAUTH_KEY = Keys.getOauthKey();

    private FragmentSignBinding binding;

    private SignViewModel mViewModel;

    public static SignFragment newInstance() {
        return new SignFragment();
    }

    private final ActivityResultLauncher<Intent> googleLoginLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> handleGoogleLoginResult(result)
    );

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSignBinding.inflate(getLayoutInflater());

        binding.loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(RegistrationFragment.newInstance());
            }
        });

        binding.signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.email.getText().toString();
                String password = binding.password.getText().toString();
                signinFirebase(email, password);
            }
        });

        binding.signGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginWithGoogle();
            }
        });

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
                    SignFragment.this.startActivity(MainPagesActivity
                            .creatIntent(requireActivity().getApplicationContext()));
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(requireActivity().getApplicationContext(), "Google login error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SignViewModel.class);
    }

    private void signinFirebase(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    Toast.makeText(requireActivity().getApplicationContext(), "Login success: "
                            + authResult.getUser().getUid(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(requireActivity().getApplicationContext(),
                            MainPagesActivity.class));
                }).addOnFailureListener(e -> Toast.makeText(requireActivity().getApplicationContext(),
                        "SignIn error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void setFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_reg_sign, fragment, null).addToBackStack("Go back")
                .commit();
    }

}