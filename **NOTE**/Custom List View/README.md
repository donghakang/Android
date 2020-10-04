# Custom List View

## setup
**1.** in the Activity.xml, call **ListView**
**2.** build XML layout for a single list, (that is pretty simple, just make sure the height is ```warp_content```)
**3.** create separate **class** to describe the content for the custom list. 
   for example ..

```JAVA
public class Reply {
    public int idx;
    public String content;
    public String writer;
    public String mine;
    public String date;

    public Reply(int idx, String content, String writer, String mine, String date) {
        this.idx = idx;
        this.content = content;
        this.writer = writer;
        this.mine = mine;
        this.date = date;
    }
}
```

**4.** Create Adapter Class to use separate ArrayAdapter.
   for example ..

```JAVA
import *;

public class ReplyAdapter extends ArrayAdapter {

    LayoutInflater inflater;
    ArrayList<Reply> arr;

    // create separate class to set elements
    class ReplyItemHolder {
        TextView tvWriter;
        TextView tvContent;
    }

    public ReplyAdapter (Activity context, ArrayList<Reply> arr) {
        super(context, R.layout.dashboard_list, arr);
        this.arr = arr;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return arr.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

/// - MOST IMPORTANT
    public View getView(int position, View convertView, ViewGroup parent) {
        ReplyAdapter.ReplyItemHolder viewHolder;
        if(convertView == null) {

            convertView = inflater.inflate(R.layout.response_list, parent, false);
            viewHolder = new ReplyItemHolder();

            // call the convertView's elements (previously defined in xml file)
            viewHolder.tvWriter = convertView.findViewById(R.id.tv_replier);
            viewHolder.tvContent = convertView.findViewById(R.id.tv_reply);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ReplyAdapter.ReplyItemHolder) convertView.getTag();
        }

        viewHolder.tvWriter.setText(arr.get(position).writer);
        viewHolder.tvContent.setText(arr.get(position).content);

        return convertView;
    }
}
```


**5.** add in the current activity

```JAVA
class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    
    ReplyAdapter adapter;
    ArrayList<Reply> arr;
    ListView customList;
    ...

    private void setup () {
        adapter = new ReplyAdapter(this, arr);
        customList.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        customList.setOnItemClickListener(this);
        customList.setOnItemLongClickListener(this);
    }
    
}
```


click [here](https://blog.naver.com/kangdh62/222091102999) to see more examples