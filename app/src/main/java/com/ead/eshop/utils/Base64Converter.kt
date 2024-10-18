import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream

// Function to convert base64 string to ImageBitmap
fun base64ToImageBitmap(base64String: String): ImageBitmap? {
    return try {
        // Remove the base64 header (e.g., "data:image/jpeg;base64,") if present
        val cleanBase64String = base64String.substringAfter(",")
        // Decode the base64 string into a byte array
        val imageBytes = Base64.decode(cleanBase64String, Base64.DEFAULT)
        // Convert the byte array to a Bitmap
        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        // Convert the Bitmap to ImageBitmap
        bitmap?.asImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

// Optional utility to convert Bitmap to base64 string
fun bitmapToBase64(bitmap: Bitmap, format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG, quality: Int = 100): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(format, quality, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}
