package net.cyclestreets.api.json;

import java.io.IOException;

public class JsonArrayHandler implements JsonItemHandler {
  public interface BeginListener {
    void begin(String name);
  }
  public interface EndListener {
    void end();
  }
  public interface Listener extends BeginListener, EndListener {
  }

  private JsonItemHandler itemHandler_;
  private BeginListener begin_;
  private EndListener end_;

  public JsonArrayHandler() {
    itemHandler_ = null;
    begin_ = null;
    end_ = null;
  } // JsonRootObject

  public void setBeginObjectListener(final BeginListener begin) { begin_ = begin; }
  public void setEndObjectListener(final EndListener end) { end_ = end; }
  public void setObjectListener(final Listener obj) {
    begin_ = obj;
    end_ = obj;
  } // setObjectListener

  public final JsonObjectHandler getObject() {
    if (itemHandler_ == null)
      itemHandler_ = new JsonObjectHandler();
    return (JsonObjectHandler)itemHandler_;
  } // getObject
  public final JsonStringHandler getString() {
    if (itemHandler_ == null)
      itemHandler_ = new JsonStringHandler();
    return (JsonStringHandler)itemHandler_;
  } // getString

  @Override
  public final void read(final String objectName, final JsonReader reader) throws IOException {
    if (begin_ != null)
      begin_.begin(objectName);

    reader.beginArray();

    while(reader.hasNext())
      if (itemHandler_ != null)
        itemHandler_.read(objectName, reader);
      else
        reader.skipValue();

    reader.endArray();

    if (end_ != null)
      end_.end();
  } // read
} // JsonArrayHandler
