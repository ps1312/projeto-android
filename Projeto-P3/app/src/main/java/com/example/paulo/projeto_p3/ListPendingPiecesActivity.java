package com.example.paulo.projeto_p3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;


public class ListPendingPiecesActivity extends AppCompatActivity {

    private RecyclerView pendingPiecesRV;

    public List<ItemList> mockData = new ArrayList<ItemList>();

    public RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pending_pieces);

        mockData.add(new ItemList("Embreagem", "Embreagem para carro 103", 1));
        mockData.add(new ItemList("Bomba d'água", "Para carro 101", 3));
        mockData.add((new ItemList("Caixa de direção", "Substituir defeito no carro 403", 1)));

        //Aplicando funcionalidades na RecyclerView
        pendingPiecesRV = findViewById(R.id.pending_pieces_rv);
        pendingPiecesRV.setLayoutManager(new LinearLayoutManager(this));
        pendingPiecesRV.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        pendingPiecesRV.setItemAnimator(new DefaultItemAnimator());
        recyclerAdapter = new RecyclerAdapter(getApplicationContext(), mockData);
        pendingPiecesRV.setAdapter(recyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_add:
                //para o comportamento mock usa-se o startActivityForResult
                startActivityForResult(new Intent(getApplicationContext(), AddItemActivity.class), 1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                mockData.add(new ItemList(data.getStringExtra("itemNome"), data.getStringExtra("itemDesc"), Integer.parseInt(data.getStringExtra("itemQuantity"))));
                recyclerAdapter.notifyItemInserted(mockData.size() - 1);
            }
        }
    }
}