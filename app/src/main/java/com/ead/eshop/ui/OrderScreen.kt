import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ead.eshop.R
import com.ead.eshop.data.model.Order
import com.ead.eshop.data.model.OrderStatusUpdate
import com.ead.eshop.data.model.sampleOrders
import com.pushpal.jetlime.ItemsList
import com.pushpal.jetlime.JetLimeColumn
import com.pushpal.jetlime.JetLimeDefaults
import com.pushpal.jetlime.JetLimeEvent
import com.pushpal.jetlime.JetLimeEventDefaults

@Composable
fun OrderScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            androidx.compose.material.IconButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back_icon),
                    contentDescription = null
                )
            }
            Text(
                text = "Orders",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)// Align text with the back button
            )
        }

        // List of orders
        sampleOrders.forEach { order ->
            OrderCard(order = order)
            Spacer(modifier = Modifier.height(12.dp)) // Space between order cards
        }
    }
}

@Composable
fun OrderCard(order: Order) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Order Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Order #${order.id}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    )
                    Text(
                        text = order.date,
                        fontSize = 14.sp,
                        color = Color(0xFF666666) // Gray color
                    )
                }
                Text(
                    text = "$${order.total}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Status and Progress bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = order.status,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
                CircularProgressIndicator(
                    progress = { order.progress },
                    modifier = Modifier.size(32.dp),
                    color = if (order.progress == 1.0f) Color(0xFF4CAF50) else Color(0xFFFFC107), // Green if delivered, yellow otherwise
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Timeline of status updates
            OrderStatusTimeline(order.statusUpdates)
        }
    }
}

@Composable
fun OrderStatusTimeline(statusUpdates: List<OrderStatusUpdate>) {
    val items = remember { statusUpdates }
    JetLimeColumn(
        itemsList = ItemsList(items),
        key = { _, item -> item.status },
        style = JetLimeDefaults.columnStyle(
            lineBrush = JetLimeDefaults.lineSolidBrush(color = Color.LightGray),
            lineThickness = 2.dp,
        ),
    ) { _, item, position ->
        JetLimeEvent(
            style = JetLimeEventDefaults.eventStyle(
                position = position,
                pointColor  = Color.LightGray,
                pointFillColor  = Color.LightGray,
                pointStrokeColor   = Color.DarkGray,
                pointRadius = 6.dp,
            )
        ) {
            ComposableContent(item = item)
        }
    }
}

@Composable
fun ComposableContent(item: OrderStatusUpdate) {
    Box(
        modifier = Modifier
            .size(12.dp)
            .clip(CircleShape)
    )

    // Status information
    Column {
        Text(
            text = item.status,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333)
        )
        Text(
            text = item.date,
            fontSize = 12.sp,
            color = Color(0xFF666666)
        )
    }
}
