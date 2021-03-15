package com.human.sqlite_jeongdoyung;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.human.sqlite_jeongdoyung.DatabaseTables.StudentTable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //현재클래스에서 사용할 멤버변수 선언(아래)
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mSqLiteDatabase;//sql템플릿(insert,select..)이 여기포함.
    private RecyclerAdapter mRecyclerAdapter;
    private List mItemList = new ArrayList<StudentVO>();//객체생성,셀렉트 쿼리결과 저장객체
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //신규 데이터베이스 객체 생성=메모리에 올리기=실행가능하게 만들기(아래)
        //=데이터베이스헬퍼클래스의 생성자 매서드 실행
        mDatabaseHelper = new DatabaseHelper(this,"school.db",null,1);
        //데이터베이스 파일 만들기(아래)
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
        //테스트로 mSqLiteDatabse 객체를 이용해서 더미데이터 인서트 테스트
        //자바의 HashMap형식과 비슷한 안드로이드 데이터형 ContentsValues형
        ContentValues contentValues = new ContentValues();
        contentValues.put(StudentTable.GRADE, 4);
        contentValues.put(StudentTable.NUMBER,20210004);
        contentValues.put(StudentTable.NAME,"아무개4");
        mSqLiteDatabase.insert(StudentTable.TABLE_NAME,null,contentValues);
        //mItemList에 셀렉트 쿼리 결과값이 셋 되어 있어야 함.

        //List 실행 리사이클러 어댑터 바인딩(아래)
        bindList();//여서는 공간마련
        //List 반영(화면출력)
        updateList();//여기에서 데이터바인딩되서 RecyclerAdaper가 화면에 재생됩니다.
    }

    private void updateList() {
        mItemList.clear();
        mItemList.addAll(getAllData());
        //안드로이드 전용 클래스이고, 메서드입니다.(아래)
        mRecyclerAdapter.notifyDataSetChanged();//어댑터에 실제값이 들어가면서 화면에 뿌려짐
    }
    //셀렉트 쿼리결과를 리턴합니다.
    private List getAllData() {
        List tableList = new ArrayList<>();//studentTable내용이 담을 예정.
        //쿼리작업에 사용될 필드명 바인딩
        String[] projection = {
                StudentTable._ID,//AutoIncrement자동증가 PK
                StudentTable.GRADE,
                StudentTable.NUMBER,
                StudentTable.NAME
        };
        //쿼리 템플릿 메서드사용(아래) Cursor커서는 레코드위치를 가지는 테이블과 같음.
        Cursor cursor = mSqLiteDatabase.query(StudentTable.TABLE_NAME,projection,null,null,null,null,"_id desc");
        //반복문조건은 커서테이블의 다음레코드가 있을때까지
        while (cursor.moveToNext()) {//student테이블에 있는 필드값을 하나씩 뽑아서
            //tableList 리스트객체에 1레코드씩 저장
            int p_id = cursor.getInt(cursor.getColumnIndexOrThrow(StudentTable._ID));
            int p_grade = cursor.getInt(cursor.getColumnIndexOrThrow(StudentTable.GRADE));
            int p_number = cursor.getInt(cursor.getColumnIndexOrThrow(StudentTable.NUMBER));
            String p_name = cursor.getString(cursor.getColumnIndexOrThrow(StudentTable.NAME));
            //매개변수가 없는 클래스의 생성자 메서드는 자바가 자동으로 만둘어줌.
            tableList.add(new StudentVO(p_id,p_grade,p_number,p_name));
        }
        return tableList;
    }

    //List 실행 리아시클러 어댑터 바인딩(아래)
    private void bindList() {
        //객체 생성
        mRecyclerAdapter = new RecyclerAdapter(mItemList);
        //리사이클러뷰xml과 어댑터 바인딩(attach) No adapter attached
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);//리사이클러 뷰의 높이를 고정한다.
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mRecyclerAdapter);//실제 attach(바인딩)
    }
}