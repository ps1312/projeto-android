package com.example.paulo.projeto_p3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class AddItemActivity extends AppCompatActivity {

    private EditText itemNomeEt;
    private EditText itemDescEt;
    private EditText itemQuantityEt;
    private Button addPieceButton;
    private Button notificacao;

    public static final String NEW_PIECE_AVAILABLE = "com.example.paulo.projeto_p3.NEW_PIECE_AVAILABLE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        itemNomeEt = findViewById(R.id.piece_name_et);
        itemDescEt = findViewById(R.id.piece_desc_et);
        itemQuantityEt = findViewById(R.id.piece_quantity_et);
        addPieceButton = findViewById(R.id.add_piece_button);
        notificacao = findViewById(R.id.notificacao_button);

        notificacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBroadcast(new Intent(NEW_PIECE_AVAILABLE));
            }
        });

        final ParseUser currentUser = ParseUser.getCurrentUser();

        addPieceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemNome = String.valueOf(itemNomeEt.getText());
                String itemDesc = String.valueOf(itemDescEt.getText());
                String itemQuantity = String.valueOf(itemQuantityEt.getText());

                if (itemNome.equals("") || itemDesc.equals("") || itemQuantity.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Por favor, preencha todos os dados", Toast.LENGTH_SHORT).show();
                } else {
                    //Adicionar no servidor
                    ParseObject piece = new ParseObject("Produto");
                    piece.put("name", itemNome);
                    piece.put("description", itemDesc);
                    piece.put("quantity", Integer.valueOf(itemQuantity));
                    piece.put("status", 0);
                    piece.put("user", currentUser);
                    piece.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null) {
                                e.printStackTrace();
                            } else {
                                startActivity(new Intent(getApplicationContext(), ListPendingPiecesActivity.class));
                            }
                        }
                    });
                }
            }
        });
    }
}
