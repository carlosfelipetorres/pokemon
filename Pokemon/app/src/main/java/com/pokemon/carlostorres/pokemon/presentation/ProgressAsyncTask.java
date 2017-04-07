package com.pokemon.carlostorres.pokemon.presentation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.pokemon.carlostorres.pokemon.R;

/**
 * Esta clase añade la funcionalidad de progress al AsyncTask
 *
 * @author <a href="mailto:carlosfelipetorres75@gmail.com">Carlos Torres J.</a>
 */
public abstract class ProgressAsyncTask<Params, Progress, Result>
        extends AsyncTask<Params, Progress, Result> {

    /** Tag para Logs **/
    private static final String TAG_LOG = ProgressAsyncTask.class.getName();

    /** Contexto actual **/
    private Activity activity;

    /** Progress Dialog **/
    private ProgressDialog mProgressDialog;

    /** Última orientación guardada **/
    private Integer mUltimaOrientacion;

    /** Indica si está bloqueado el recurso de la orientación de pantalla **/
    private static boolean bloqueo = false;

    /**
     * Constructor para el progress async task
     *
     * @param activity
     *         Donde se va a correr el progress async task
     */
    public ProgressAsyncTask(Activity activity) {
        this.activity = activity;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (!bloqueo) {
            bloqueo = true;
            mUltimaOrientacion = activity.getRequestedOrientation();
            activity.setRequestedOrientation(
                    ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        }
        mProgressDialog = ProgressDialog.show(activity, "", activity.getString(R.string.loading));

    }

    /**
     * Override this method to perform a computation on a background thread. The specified
     * parameters are the parameters passed to {@link #execute} by the caller of this task.
     * <p/>
     * This method can call {@link #publishProgress} to publish updates on the UI thread.
     *
     * @param params
     *         The parameters of the task.
     *
     * @return A result, defined by the subclass of this task.
     *
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected abstract Result doInBackground(Params... params);

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            try {
                mProgressDialog.dismiss();
            } catch (Exception e) {
                Log.e(TAG_LOG, "Un error ha ocurrido mientras se ocultaba el Dialog", e);
            }
        }
        if (mUltimaOrientacion != null && bloqueo) {
            activity.setRequestedOrientation(mUltimaOrientacion);
            bloqueo = false;
        }
    }

}
