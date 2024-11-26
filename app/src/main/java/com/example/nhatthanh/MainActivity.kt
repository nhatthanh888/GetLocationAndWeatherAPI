@file:Suppress("DEPRECATION")

package com.example.nhatthanh

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nhatthanh.ui.theme.GetLocationAndWeatherAPITheme
import com.example.nhatthanh.viewmodel.WeatherViewModel
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {

                }

                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {

                }

                else -> {
                }
            }
        }
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        setContent {
            GetLocationAndWeatherAPITheme {
                LocationDisplay()
            }
        }
    }
}

@Composable
fun LocationDisplay() {
    val viewModel = hiltViewModel<WeatherViewModel>()
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    var locationText by remember { mutableStateOf("Lấy vị trí") }
    var latitude by remember { mutableStateOf<Double?>(null) }
    var longitude by remember { mutableStateOf<Double?>(null) }

    val weatherResponse = viewModel.weatherResponse.value

    fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationText = "Vui lòng cấp quyền truy cập vị trí!"
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                latitude = location.latitude
                longitude = location.longitude

                val geocoder = Geocoder(context, Locale.getDefault())
                val address = geocoder.getFromLocation(
                    latitude!!,
                    longitude!!,
                    1
                )?.get(0)?.getAddressLine(0)

                locationText = address ?: "Không thể tìm thấy địa chỉ."
            } else {
                locationText = "Không thể lấy vị trí hiện tại."
            }
        }.addOnFailureListener {
            locationText = "Lỗi khi lấy vị trí."
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = locationText)
        latitude?.let { Text(text = "Latitude: $it") }
        longitude?.let { Text(text = "Longitude: $it") }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                getLocation()
            }
        ) {
            Text("Get Location")
        }

        Button(
            onClick = {
                latitude?.let { lat ->
                    longitude?.let { long ->
                        viewModel.getCurrentWeather(lat, long)
                    }
                }
            }
        ) {
            Text("Get Information Weather")
        }

        Spacer(modifier = Modifier.height(16.dp))
        weatherResponse?.let {
            Text("Weather: $it")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GetLocationAndWeatherAPITheme {
        LocationDisplay()
    }
}