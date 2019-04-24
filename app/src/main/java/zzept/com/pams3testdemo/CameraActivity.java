package zzept.com.pams3testdemo;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CameraActivity extends Activity implements OnClickListener {

    public static final int REQUEST_CODE_GET_PHOTO = 1;
    private static final int REQUEST_CODE_VIEW_PHOTO = 2;
    // private static final int REQUEST_CODE_ALL_VIEW_PHOTO=2;
    private Button mBt_camera;
    private Button mBt_back;
    private Button mBt_delete;
    private Button mBt_view;
    private Instrumentation inst = new Instrumentation();
    private String currentPath = null;
    private MediaAdapter adapter;
    private ListView mLv_info;
    private String[] listFile;
    private int mlistselect = 0;
    private long lastTime = 0;
    private boolean canTakePhoto = false;

    /**
     * @author TOPS
     */
    public static class MediaListCellData {
        int type = 0;
        int id = -1;
        String path = "";
        int iconId = R.drawable.ic_launcher_background;

        public MediaListCellData(String path, int id) {
            this(path);
            this.id = id;
        }

        public MediaListCellData(String path) {
            this.path = path;
            if (path.endsWith(".jpg")) {
                iconId = R.drawable.ic_launcher_background;
                type = MediaType.PHOTO;

            }
        }

    }

    /**
     * @author TOPS
     */
    public static class MediaType {
        static final int PHOTO = 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        initFrame();
    }

    @Override
    protected void onResume() {
        super.onResume();
        File files = getMediaDir();
        if (!files.exists()) {
            files.mkdir();
        }
        String[] listFile = files.list();
        List<MediaListCellData> m_list = new ArrayList<>();
        if(listFile!=null){
            for (int i = listFile.length - 1; i >= 0; i--) {
                m_list.add(new MediaListCellData(listFile[i]));
            }
        }
        adapter.clear();
        adapter.add(m_list);
        adapter.notifyDataSetChanged();
        inst.setInTouchMode(true);
        inst.setInTouchMode(false);
        canTakePhoto = true;
    }

    public void initFrame() {
        mBt_camera = (Button) this.findViewById(R.id.bt_camera);
        mBt_back = (Button) this.findViewById(R.id.bt_back);
        mBt_delete = (Button) this.findViewById(R.id.bt_delete);
        mBt_view = (Button) this.findViewById(R.id.bt_view);
        mBt_camera.setOnClickListener(this);
        mBt_back.setOnClickListener(this);
        mBt_delete.setOnClickListener(this);
        mBt_view.setOnClickListener(this);

        mLv_info = (ListView) this.findViewById(R.id.lv_info);
        adapter = new MediaAdapter(this);
        mLv_info.setAdapter(adapter);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                File files = getMediaDir();
                if (!files.exists()) {
                    files.mkdir();
                }
                listFile = files.list();
                List<MediaListCellData> m_list = new ArrayList<MediaListCellData>();
                if(listFile!=null){
                    for (int i = listFile.length - 1; i >= 0; i--) {
                        m_list.add(new MediaListCellData(listFile[i]));
                    }
                }
                adapter.add(m_list);
                adapter.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        mLv_info.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                mlistselect = position;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("image/*");
                intent.setDataAndType(Uri.fromFile(new File(getMediaDir() + "/" + adapter.getItem(position).path)), "image/*");
                startActivityForResult(intent, REQUEST_CODE_VIEW_PHOTO);

            }
        });

        mLv_info.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                mlistselect = arg2;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        if (listFile!=null&&listFile.length > 0) {
            inst.setInTouchMode(false);
            mLv_info.setSelection(0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_camera:
                if (canTakePhoto) {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastTime > 500) {
                        callMycamera();
                        lastTime = currentTime;
                        canTakePhoto = false;
                    }
                }
                break;
            case R.id.bt_back:
                finish();
                break;

            case R.id.bt_delete:
                File files = getMediaDir();

                if (!files.exists()) {
                    files.mkdir();
                }

                String[] listFile = files.list();
                if (listFile == null) {
                    break;
                }
                if (listFile.length == 0) {
                    break;
                }
                if (listFile != null) {
                    Intent intent = new Intent();
                    intent.setClass(CameraActivity.this, Activity_Dialog.class);
                    intent.putExtra("strTitle", "提示");
                    intent.putExtra("strContent", "是否删除");
                    intent.putExtra("sort", 1);
                    startActivityForResult(intent, 0);
                }
                break;
            case R.id.bt_view:
                File filess = getMediaDir();

                if (!filess.exists()) {
                    filess.mkdir();
                }

                String[] listFiles = filess.list();
                if (listFiles == null) {
                    return;
                }
                if (listFiles.length == 0) {
                    return;
                }
                Intent intentImage = new Intent(Intent.ACTION_VIEW);
                intentImage.setType("image/*");
                intentImage.setDataAndType(Uri.fromFile(new File(getMediaDir() + "/" + adapter.getItem(mlistselect).path)), "image/*");
                startActivityForResult(intentImage, REQUEST_CODE_VIEW_PHOTO);
                break;
            default:
                break;

        }

    }

    private void callMycamera() {
        Intent intent = new Intent();
        intent.setClass(CameraActivity.this, CameraInfoActivity.class);
        startActivityForResult(intent, REQUEST_CODE_GET_PHOTO);

    }

    /**
     * @author TOPS
     */
    static class MediaAdapter extends BaseAdapter {
        private Context context;
        private List<MediaListCellData> m_list = new ArrayList<MediaListCellData>();

        public MediaAdapter(Context context) {
            this.context = context;
        }

        public void add(List<MediaListCellData> list) {
            m_list = list;
        }

        public void clear() {
            m_list.clear();
        }

        @Override
        public int getCount() {
            return m_list.size();
        }

        @Override
        public MediaListCellData getItem(int position) {
            return m_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageHolder holder = new ImageHolder();
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.media_list_cell, null);
                holder.ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
                holder.tvPath = (TextView) convertView.findViewById(R.id.tvPath);
                convertView.setTag(holder);
            } else {
                holder = (ImageHolder) convertView.getTag();
            }
            MediaListCellData data = getItem(position);
            Log.e("position", position + "");
            holder.ivIcon.setImageResource(data.iconId);
            holder.tvPath.setText(data.path);
            return convertView;
        }

        class ImageHolder {
            public ImageView ivIcon;
            public TextView tvPath;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (1 == requestCode && resultCode == 1) {
            File files = getMediaDir();
            if (!files.exists()) {
                files.mkdir();
            }
            String[] listFile = files.list();
            List<MediaListCellData> m_list = new ArrayList<>();
            for (int i = listFile.length - 1; i >= 0; i--) {
                m_list.add(new MediaListCellData(listFile[i]));
            }
            adapter.clear();
            adapter.add(m_list);
            adapter.notifyDataSetChanged();
            inst.setInTouchMode(true);
            inst.setInTouchMode(false);
            mlistselect = 0;
            mLv_info.setSelection(mlistselect);
        }
        if (requestCode == 0 && resultCode == Activity_Dialog.RESULT_SURE) {
            deleteFile();
            inst.setInTouchMode(true);
            inst.setInTouchMode(false);
        }

    }


    private void deleteFile() {
        if (listFile == null) {
            return;
        }
        File files = getMediaDir();
        listFile = files.list();
        if (listFile.length <= 0) {
            return;
        }
        File file = new File(getMediaDir() + "/" + adapter.getItem(mlistselect).path);
        if (file.exists()) {
            file.delete();
            listFile = files.list();
        }

        List<MediaListCellData> m_list = new ArrayList<>();
        for (int i = listFile.length - 1; i >= 0; i--) {
            m_list.add(new MediaListCellData(listFile[i]));
        }
        if (mlistselect >= m_list.size() || mlistselect < 0) {
            mlistselect = mlistselect - 1;
        }
        adapter.clear();
        adapter = null;
        adapter = new MediaAdapter(this);
        mLv_info.setAdapter(adapter);
        adapter.add(m_list);
        adapter.notifyDataSetChanged();
        mLv_info.requestFocusFromTouch();
        mLv_info.setSelection(mlistselect);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_UP) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_ENTER:
                    if (canTakePhoto) {
                        long currentTime = System.currentTimeMillis();
                        if (currentTime - lastTime > 500) {//
                            callMycamera();
                            lastTime = currentTime;
                            canTakePhoto = false;
                        }
                    }
                    return false;
                case KeyEvent.KEYCODE_BACK:
                    finish();
                    return true;
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    File files = getMediaDir();
                    if (!files.exists()) {
                        files.mkdir();
                    }
                    String[] listFile = files.list();
                    if (listFile != null) {
                        KeyEvent keyEvent = new KeyEvent(event.getAction(), KeyEvent.KEYCODE_ENTER);
                        mLv_info.dispatchKeyEvent(keyEvent);
                    }
                    return true;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    File filess = getMediaDir();

                    if (!filess.exists()) {
                        filess.mkdir();
                    }

                    String[] listFiles = filess.list();
                    if (listFiles == null) {
                        break;
                    }
                    if (listFiles.length == 0) {
                        break;
                    }
                    if (listFiles != null) {
                        Intent intent = new Intent();
                        intent.setClass(CameraActivity.this, Activity_Dialog.class);
                        intent.putExtra("strTitle", "提示");
                        intent.putExtra("strContent", "是否删除？");
                        intent.putExtra("sort", 1);
                        startActivityForResult(intent, 0);
                    }

                    return true;
                default:
                    break;

            }
        }
        if (event.getAction() == KeyEvent.ACTION_UP) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    File files = getMediaDir();
                    if (!files.exists()) {
                        files.mkdir();
                    }
                    String[] listFile = files.list();
                    if (listFile != null) {
                        KeyEvent keyEvent = new KeyEvent(event.getAction(), KeyEvent.KEYCODE_ENTER);
                        mLv_info.dispatchKeyEvent(keyEvent);
                    }
                    return true;
                default:
                    break;
            }

        }

        return super.dispatchKeyEvent(event);
    }

    public File getMediaDir() {
        File dir = new File(Environment.getExternalStorageDirectory(), "pams3_camera");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

}
