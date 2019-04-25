package com.activities.dwtaplin.jobsearchfinal.database;

import android.content.Context;
import android.util.Pair;

import com.activities.dwtaplin.jobsearchfinal.R;
import com.activities.dwtaplin.jobsearchfinal.actors.User;
import com.activities.dwtaplin.jobsearchfinal.components.Document;
import com.activities.dwtaplin.jobsearchfinal.components.Job;
import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ServerManager {
    private Context context;
    private String and;
    public ServerManager(Context context){
        this.context = context;
        and = context.getString(R.string.and);
    }

    public ArrayList<Job> getWatchList(User user) {
        try {
            HttpURLConnection httpURLConnection = getHttpURLConnection(context.getString(R.string.get_watchlist));
            OutputStream outStream = httpURLConnection.getOutputStream();
            BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(outStream, context.getString(R.string.utf8)));
            String postData;
            postData = prepareEncodedStatement(context.getString(R.string.user), String.valueOf(user.getServerId()));
            bWriter.write(postData);
            bWriter.flush();
            bWriter.close();
            outStream.close();
            InputStream inStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream, context.getString(R.string.iso)));
            String result = "";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inStream.close();
            httpURLConnection.disconnect();
            ArrayList watchList = new JsonManager(context).decodeJobs(result);
            return watchList;

        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User logIn(String email, String passWord){
        try {
            HttpURLConnection httpURLConnection = getHttpURLConnection(context.getString(R.string.log_in));
            OutputStream outStream = httpURLConnection.getOutputStream();
            BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(outStream, context.getString(R.string.utf8)));
            String postData;
            postData = prepareEncodedStatement(context.getString(R.string.email), email) + and + prepareEncodedStatement(context.getString(R.string.password), passWord);
            bWriter.write(postData);
            bWriter.flush();
            bWriter.close();
            outStream.close();
            InputStream inStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream, context.getString(R.string.iso)));
            String result = "";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inStream.close();
            httpURLConnection.disconnect();
            User user = new JsonManager(context).getUser(result);
            return user;

        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public Pair<Boolean, String> addListing(int userId, Job job) {
        try {
            HttpURLConnection httpURLConnection = getHttpURLConnection(context.getString(R.string.insert_listing));
            OutputStream outStream = httpURLConnection.getOutputStream();
            BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(outStream, context.getString(R.string.utf8)));
            String postData;
            HashMap<String, String> toPost = new HashMap<>();
            toPost.put(context.getString(R.string.listing_title), job.getTitle());
            toPost.put(context.getString(R.string.company), job.getCompany());
            toPost.put(context.getString(R.string.desc), job.getDesc());
            toPost.put(context.getString(R.string.listing_location), job.getCity());
            toPost.put(context.getString(R.string.qualification), job.getQual());
            toPost.put(context.getString(R.string.user_id), String.valueOf(userId));
            toPost.put(context.getString(R.string.close), job.getCloseDateAsString());
            toPost.put(context.getString(R.string.listed), job.getListedDateAsString());
            toPost.put(context.getString(R.string.type), String.valueOf(job.getType()));
            if(job.getWage() != null)
               toPost.put(context.getString(R.string.wage), String.valueOf(job.getWage()));
             else {
                toPost.put(context.getString(R.string.min), String.valueOf(job.getSalaryMin()));
                toPost.put(context.getString(R.string.max), String.valueOf(job.getSalaryMax()));
            }
            postData = preparePostData(toPost);
            bWriter.write(postData);
            bWriter.flush();
            bWriter.close();
            outStream.close();
            InputStream inStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream, context.getString(R.string.iso)));
            String result = "";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inStream.close();
            httpURLConnection.disconnect();
            return new Pair<>(true, result);

        }catch(Exception e) {
            e.printStackTrace();
            return new Pair<>(false, "Job listing already exists");
        }

    }

    public ArrayList<Job> getListings() {
        try {
            HttpURLConnection httpURLConnection = getHttpURLConnection(context.getString(R.string.get_listings));
            OutputStream outStream = httpURLConnection.getOutputStream();
            BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(outStream, context.getString(R.string.utf8)));
            String postData;
            postData = prepareEncodedStatement(context.getString(R.string.user_id), String.valueOf(100));
            bWriter.write(postData);
            bWriter.flush();
            bWriter.close();
            outStream.close();
            InputStream inStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream, context.getString(R.string.iso)));
            String result = "";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inStream.close();
            httpURLConnection.disconnect();
            ArrayList jobs = new JsonManager(context).decodeJobs(result);
            return jobs;

        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public boolean updateWatchlist(int user, int listing){
        try {
            HttpURLConnection httpURLConnection = getHttpURLConnection(context.getString(R.string.update_watchlist));
            OutputStream outStream = httpURLConnection.getOutputStream();
            BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(outStream, context.getString(R.string.utf8)));
            String postData;
            HashMap<String, String> toPost = new HashMap<>();
            toPost.put(context.getString(R.string.user), String.valueOf(user));
            toPost.put(context.getString(R.string.listing), String.valueOf(listing));
            postData = preparePostData(toPost);
            bWriter.write(postData);
            bWriter.flush();
            bWriter.close();
            outStream.close();
            InputStream inStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream, context.getString(R.string.iso)));
            String result = "";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inStream.close();
            httpURLConnection.disconnect();
            System.out.println(result);
            if(result.equals("added"))
                return true;
            else
                return false;

        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private String preparePostData(HashMap<String, String> map){
        String postData ="";
        Iterator i = map.entrySet().iterator();
        while(i.hasNext()){
            Map.Entry pair = (Map.Entry)i.next();
            if(postData.equals(""))
                postData += prepareEncodedStatement((String)pair.getKey(), (String)pair.getValue());
            else
                postData += and + prepareEncodedStatement((String)pair.getKey(), (String)pair.getValue());
        }
        return postData;
    }
    private String prepareEncodedStatement(String arg1, String arg2) {
        try {
            return URLEncoder.encode(arg1, "UTF-8") + "=" + URLEncoder.encode(arg2, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    private HttpURLConnection getHttpURLConnection(String Url) {
        java.net.URL url = null;
        try {
            url = new URL(Url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(context.getResources().getString(R.string.post));
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            return httpURLConnection;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addLatLngToListing(int id, LatLng latLng) {
        try {
            HttpURLConnection httpURLConnection = getHttpURLConnection(context.getString(R.string.add_location));
            OutputStream outStream = httpURLConnection.getOutputStream();
            BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(outStream, context.getString(R.string.utf8)));
            String postData;
            postData = prepareEncodedStatement(context.getString(R.string.id), String.valueOf(id)) + and + prepareEncodedStatement(context.getString(R.string.lat), String.valueOf(latLng.latitude))
                + and + prepareEncodedStatement(context.getString(R.string.lng), String.valueOf(latLng.longitude));
            bWriter.write(postData);
            bWriter.flush();
            bWriter.close();
            outStream.close();
            InputStream inStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream, context.getString(R.string.iso)));
            String result = "";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inStream.close();
            httpURLConnection.disconnect();
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    public boolean register(User user, String passWord, String token) {
        try {
            HttpURLConnection httpURLConnection = getHttpURLConnection(context.getString(R.string.register));
            OutputStream outStream = httpURLConnection.getOutputStream();
            BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(outStream, context.getString(R.string.utf8)));
            String postData;
            HashMap<String, String> toPost = new HashMap<>();
            toPost.put(context.getString(R.string.f_name), user.getFirstName());
            toPost.put(context.getString(R.string.last_name), user.getLastName());
            toPost.put(context.getString(R.string.u_name), user.getUserName());
            toPost.put(context.getString(R.string.email), user.getEmail());
            toPost.put(context.getString(R.string._desc), user.getDesc());
            toPost.put(context.getString(R.string.location), user.getLocation());
            toPost.put(context.getString(R.string.qual), user.getQual());
            toPost.put(context.getString(R.string.token), token);
            toPost.put(context.getString(R.string.password), passWord);
            postData = preparePostData(toPost);
            bWriter.write(postData);
            bWriter.flush();
            bWriter.close();
            outStream.close();
            InputStream inStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream, context.getString(R.string.iso)));
            String result = "";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inStream.close();
            httpURLConnection.disconnect();
            if((!result.isEmpty())  && (!result.equals("")))
                return true;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public void sendRegistrationToServer(String refreshedToken){

    }
    public void sendFirebaseMessage(String token){
        try {
            HttpURLConnection httpURLConnection = getHttpURLConnection(context.getString(R.string.send_FCM));
            OutputStream outStream = httpURLConnection.getOutputStream();
            BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(outStream, context.getString(R.string.utf8)));
            String postData;
            postData = prepareEncodedStatement(context.getString(R.string.id), token) + and + prepareEncodedStatement(context.getString(R.string.msg), "Message from server to FCM");
            bWriter.write(postData);
            bWriter.flush();
            bWriter.close();
            outStream.close();
            httpURLConnection.disconnect();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void updateToken(int id, String token) {
        try {
            HttpURLConnection httpURLConnection = getHttpURLConnection(context.getString(R.string.update_token));
            OutputStream outStream = httpURLConnection.getOutputStream();
            BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(outStream, context.getString(R.string.utf8)));
            String postData;
            postData = prepareEncodedStatement(context.getString(R.string.id), String.valueOf(id)) + and + prepareEncodedStatement(context.getString(R.string.token), token);
            bWriter.write(postData);
            bWriter.flush();
            bWriter.close();
            outStream.close();
            InputStream inStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream, context.getString(R.string.iso)));
            String result = "";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inStream.close();
            System.out.println(result);
            httpURLConnection.disconnect();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public boolean addFile(Document doc, User user){
        try{
            HttpURLConnection httpURLConnection = getHttpURLConnection(context.getString(R.string.add_file));
            OutputStream outStream = httpURLConnection.getOutputStream();
            BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(outStream, context.getString(R.string.utf8)));
            String postData = "";
            //postData = prepareEncodedStatement(context.getString(R.string.id), String.valueOf(id)) + and + prepareEncodedStatement(context.getString(R.string.token), token);
            bWriter.write(postData);
            bWriter.flush();
            bWriter.close();
            outStream.close();
            InputStream inStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream, context.getString(R.string.iso)));
            String result = "";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inStream.close();
            httpURLConnection.disconnect();
            if(result.equals("success"))
                return true;
            else
                return false;


        } catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean apply(Job job, User user) {
        return true;
    }

    public void viewedJob(Job job, User user) {
        try{
            HttpURLConnection httpURLConnection = getHttpURLConnection(context.getString(R.string.reject));
            OutputStream outStream = httpURLConnection.getOutputStream();
            BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(outStream, context.getString(R.string.utf8)));
            String postData;
            HashMap map = new HashMap();
            map.put(R.string.job, String.valueOf(job.getId()));
            map.put(R.string.user, String.valueOf(user.getServerId()));
            postData = preparePostData(map);
            bWriter.write(postData);
            bWriter.flush();
            bWriter.close();
            outStream.close();
            InputStream inStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream, context.getString(R.string.iso)));
            String result = "";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inStream.close();
            
            httpURLConnection.disconnect();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean addFile(String type, InputStream inputStream, String name, User user){
        try {
            byte[] fileAsBytes = inputStreamToByteArray(inputStream);
            HttpURLConnection httpURLConnection = getHttpURLConnection(context.getString(R.string.add_file));
            OutputStream outStream = httpURLConnection.getOutputStream();
            BufferedWriter bWriter = null;
            bWriter = new BufferedWriter(new OutputStreamWriter(outStream, context.getString(R.string.utf8)));
            String postData;
            HashMap map = new HashMap();
            map.put("name", name);
            map.put("type", type);
            map.put("file", String.valueOf(fileAsBytes));
            postData = preparePostData(map);
            bWriter.write(postData);
            bWriter.flush();
            bWriter.close();
            outStream.close();
            InputStream inStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream, context.getString(R.string.iso)));
            String result = "";
            String line;
            System.out.println(postData);
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inStream.close();
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean uploadFile(String type, InputStream inputStream, String name, User user) {
        String crlf = "\r\n";
        String twoHyphens = "--";
        String boundary =  "*****";
        try {
            byte[] fileAsBytes = inputStreamToByteArray(inputStream);
            HttpURLConnection httpURLConnection = getHttpURLConnection(context.getString(R.string.upload_file));
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Cache-Control", "no-cache");
            httpURLConnection.setRequestProperty(
                    "Content-Type", "multipart/form-data;boundary=" + boundary);
            DataOutputStream request = null;
            request = new DataOutputStream(
                    httpURLConnection.getOutputStream());
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"" +
                    "file" + "\";filename=\"" +
                    name + "." + type + "\"" + crlf);
            request.writeBytes(crlf);
            request.write(fileAsBytes);
            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary +
                    twoHyphens + crlf);
            request.flush();
            request.close();
            InputStream inStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream, context.getString(R.string.iso)));
            String result = "";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            System.out.println(result);
            bufferedReader.close();
            inStream.close();
            httpURLConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private byte[] inputStreamToByteArray(InputStream inputStream){
        ByteArrayOutputStream ous = null;
        try {
            byte[] buffer = new byte[4096];
            ous = new ByteArrayOutputStream();
            int read = 0;
            while ((read = inputStream.read(buffer)) != -1) {
                ous.write(buffer, 0, read);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ous != null)
                    ous.close();
            } catch (IOException e) {
            }

            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
            }
        }
        return ous.toByteArray();

    }

    public boolean addFile(){
        return false;
    }
    
}
