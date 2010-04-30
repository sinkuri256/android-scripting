/*
 * Copyright (C) 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.ase.jsonrpc;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;

import com.google.ase.facade.Event;

public class JsonBuilder {

  private JsonBuilder() {
    // This is a utility class.
  }

  public static Object build(Object data) throws JSONException {
    if (data == null) {
      return JSONObject.NULL;
    }
    if (data instanceof Integer) {
      return data;
    }
    if (data instanceof Float) {
      return data;
    }
    if (data instanceof Double) {
      return data;
    }
    if (data instanceof Long) {
      return data;
    }
    if (data instanceof String) {
      return data;
    }
    if (data instanceof Boolean) {
      return data;
    }
    if (data instanceof JSONObject) {
      return data;
    }
    if (data instanceof JSONArray) {
      return data;
    }
    if (data instanceof Set<?>) {
      List<Object> items = new ArrayList<Object>((Set<?>) data);
      return buildJsonList(items);
    }
    if (data instanceof List<?>) {
      return buildJsonList((List<?>) data);
    }
    if (data instanceof Address) {
      return buildJsonAddress((Address) data);
    }
    if (data instanceof Location) {
      return buildJsonLocation((Location) data);
    }
    if (data instanceof Bundle) {
      return buildJsonBundle((Bundle) data);
    }
    if (data instanceof Intent) {
      return buildJsonIntent((Intent) data);
    }
    if (data instanceof Event) {
      return buildJsonEvent((Event) data);
    }
    throw new JSONException("Failed to build JSON result.");
  }

  private static <T> JSONArray buildJsonList(final List<T> list) throws JSONException {
    JSONArray result = new JSONArray();
    for (T item : list) {
      result.put(build(item));
    }
    return result;
  }

  private static JSONObject buildJsonAddress(Address address) throws JSONException {
    JSONObject result = new JSONObject();
    result.put("admin_area", address.getAdminArea());
    result.put("country_code", address.getCountryCode());
    result.put("country_name", address.getCountryName());
    result.put("feature_name", address.getFeatureName());
    result.put("phone", address.getPhone());
    result.put("locality", address.getLocality());
    result.put("postal_code", address.getPostalCode());
    result.put("sub_admin_area", address.getSubAdminArea());
    result.put("thoroughfare", address.getThoroughfare());
    result.put("url", address.getUrl());
    return result;
  }

  private static JSONObject buildJsonLocation(Location location) throws JSONException {
    JSONObject result = new JSONObject();
    result.put("altitude", location.getAltitude());
    result.put("latitude", location.getLatitude());
    result.put("longitude", location.getLongitude());
    result.put("time", location.getTime());
    result.put("accuracy", location.getAccuracy());
    result.put("speed", location.getSpeed());
    result.put("provider", location.getProvider());
    return result;
  }

  private static JSONObject buildJsonBundle(Bundle bundle) throws JSONException {
    JSONObject result = new JSONObject();
    for (String key : bundle.keySet()) {
      result.put(key, build(bundle.get(key)));
    }
    return result;
  }

  private static JSONObject buildJsonIntent(Intent data) throws JSONException {
    JSONObject result = new JSONObject();
    result.put("data", data.getDataString());
    result.put("extras", build(data.getExtras()));
    return result;
  }

  private static JSONObject buildJsonEvent(Event event) throws JSONException {
    JSONObject result = new JSONObject();
    result.put("name", event.getName());
    result.put("data", build(event.getData()));
    return result;
  }
}
