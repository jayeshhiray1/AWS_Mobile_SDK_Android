package com.wipro.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory

import android.widget.Toast
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException
import android.os.AsyncTask

import android.util.Log


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create an instance of CognitoCachingCredentialsProvider
        val cognitoProvider = CognitoCachingCredentialsProvider(
            this.applicationContext,
            "identity-pool-id",
            Regions.US_WEST_2
        )

        // Create LambdaInvokerFactory, to be used to instantiate the Lambda proxy.
        val factory =
            LambdaInvokerFactory(this.applicationContext, Regions.US_WEST_2, cognitoProvider)

        // Create the Lambda proxy object with a default Json data binder.
        // You can provide your own data binder by implementing
        // LambdaDataBinder.
        val myInterface = factory.build(MyInterface::class.java)

        val request = RequestClass("John", "Doe")
        // The Lambda function invocation results in a network call.
        // Make sure it is not called from the main thread.
        object : AsyncTask<RequestClass, Void, ResponseClass>() {
            override fun doInBackground(vararg params: RequestClass): ResponseClass? {
                // invoke "echo" method. In case it fails, it will throw a
                // LambdaFunctionException.
                try {
                    return myInterface.AndroidBackendLambdaFunction(params[0])
                } catch (lfe: LambdaFunctionException) {
                    Log.e("Tag", "Failed to invoke echo", lfe)
                    return null
                }

            }

            override fun onPostExecute(result: ResponseClass?) {
                if (result == null) {

                    return
                }

                Log.e("Jayesh",result.greetings.toString());

                // Do a toast
                Toast.makeText(this@MainActivity, result.greetings, Toast.LENGTH_LONG).show()
            }
        }.execute(request)

    }

}
