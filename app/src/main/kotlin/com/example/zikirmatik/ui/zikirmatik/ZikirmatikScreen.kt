package com.example.zikirmatik.ui.zikirmatik

import com.example.zikirmatik.ui.components.AdBanner

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeMute
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zikirmatik.model.Dhikr
import com.example.zikirmatik.model.TasbihTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZikirmatikScreen(
    viewModel: ZikirmatikViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    var showDhikrSheet by remember { mutableStateOf(false) }
    var showThemeSheet by remember { mutableStateOf(false) }
    var showHistorySheet by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }
    var showTargetDialog by remember { mutableStateOf(false) }
    var showAddCustomDhikrDialog by remember { mutableStateOf(false) }

    val primaryColor = Color(state.selectedTheme.primaryHex)
    val secondaryColor = Color(state.selectedTheme.secondaryHex)
    val surfaceColor = Color(state.selectedTheme.surfaceHex)
    val beadColor = Color(state.selectedTheme.beadHex)
    val accentColor = Color(state.selectedTheme.accentHex)

    if (state.isFullScreenEnabled) {
        // Full screen tap mode
        FullScreenZikirmatik(
            state = state,
            onTap = { viewModel.increment() },
            onExit = { viewModel.toggleFullScreen() },
            primaryColor = primaryColor,
            accentColor = accentColor
        )
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Zikirmatik",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Toplam: ${state.totalLifetimeCount}",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.75f)
                        )
                    }
                },
                actions = {
                    // Sound toggle
                    IconButton(onClick = { viewModel.toggleSound() }) {
                        Icon(
                            imageVector = if (state.isSoundEnabled) Icons.AutoMirrored.Filled.VolumeUp else Icons.AutoMirrored.Filled.VolumeMute,
                            contentDescription = "Ses",
                            tint = if (state.isSoundEnabled) accentColor else Color.White.copy(alpha = 0.5f)
                        )
                    }
                    // Vibration toggle
                    IconButton(onClick = { viewModel.toggleVibration() }) {
                        Icon(
                            imageVector = Icons.Default.Vibration,
                            contentDescription = "Titreşim",
                            tint = if (state.isVibrationEnabled) accentColor else Color.White.copy(alpha = 0.5f)
                        )
                    }
                    // Full screen toggle
                    IconButton(onClick = { viewModel.toggleFullScreen() }) {
                        Icon(
                            imageVector = Icons.Default.Fullscreen,
                            contentDescription = "Tam Ekran Modu",
                            tint = Color.White
                        )
                    }
                    // Theme picker
                    IconButton(onClick = { showThemeSheet = true }) {
                        Icon(
                            imageVector = Icons.Default.Palette,
                            contentDescription = "Temalar",
                            tint = Color.White
                        )
                    }
                    // History
                    IconButton(onClick = { showHistorySheet = true }) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "Geçmiş",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primaryColor
                )
            )
        },
        bottomBar = {
            AdBanner()
        },
        containerColor = surfaceColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Target Reached Banner
            AnimatedVisibility(
                visible = state.showTargetReachedBanner,
                enter = slideInVertically() + fadeIn(),
                exit = slideOutVertically() + fadeOut()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = accentColor),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Tebrikler! ${state.target} zikir tamamlandı.",
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                fontSize = 14.sp
                            )
                        }
                        IconButton(
                            onClick = { viewModel.dismissTargetBanner() },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Kapat",
                                tint = Color.Black
                            )
                        }
                    }
                }
            }

            // Top Dhikr Info Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDhikrSheet = true },
                colors = CardDefaults.cardColors(containerColor = primaryColor.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.dp, accentColor.copy(alpha = 0.3f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (state.currentDhikr.arabic.isNotBlank()) {
                        Text(
                            text = state.currentDhikr.arabic,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = accentColor,
                            textAlign = TextAlign.Center,
                            lineHeight = 32.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                    Text(
                        text = state.currentDhikr.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    if (state.currentDhikr.meaning.isNotBlank()) {
                        Text(
                            text = state.currentDhikr.meaning,
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Progress bar
                    if (state.target > 0) {
                        val progress = (state.count.toFloat() / state.target.toFloat()).coerceIn(0f, 1f)
                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = accentColor,
                            trackColor = Color.White.copy(alpha = 0.15f)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Kalan: ${maxOf(0, state.target - state.count)}",
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.6f)
                            )
                            Text(
                                text = "Hedef: ${state.target}",
                                fontSize = 11.sp,
                                color = accentColor
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Physical Zikirmatik Device
            PhysicalZikirmatikDevice(
                count = state.count,
                lapCount = state.lapCount,
                target = state.target,
                primaryColor = primaryColor,
                secondaryColor = secondaryColor,
                beadColor = beadColor,
                accentColor = accentColor,
                onIncrement = { viewModel.increment() },
                onDecrement = { viewModel.decrement() },
                onResetClick = { showResetDialog = true },
                onTargetClick = { showTargetDialog = true }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Quick Dhikr Switcher Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(
                    onClick = { showDhikrSheet = true },
                    border = BorderStroke(1.dp, accentColor.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("Zikir Değiştir", color = Color.White, fontSize = 13.sp)
                }

                OutlinedButton(
                    onClick = { showTargetDialog = true },
                    border = BorderStroke(1.dp, accentColor.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        if (state.target > 0) "Hedef: ${state.target}" else "Hedef: ∞",
                        color = accentColor,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }

    // Reset Confirmation Dialog
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("Sayacı Sıfırla") },
            text = { Text("Mevcut sayacınızı (${state.count}) sıfırlamak istediğinizden emin misiniz?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.resetCount()
                        showResetDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Evet, Sıfırla")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("İptal")
                }
            }
        )
    }

    // Target Selection Dialog
    if (showTargetDialog) {
        TargetSelectionDialog(
            currentTarget = state.target,
            onDismiss = { showTargetDialog = false },
            onTargetSelected = {
                viewModel.setTarget(it)
                showTargetDialog = false
            }
        )
    }

    // Dhikr Selection Sheet
    if (showDhikrSheet) {
        DhikrSelectionSheet(
            allDhikrs = state.allDhikrs,
            selectedDhikr = state.currentDhikr,
            onSelect = {
                viewModel.selectDhikr(it)
                showDhikrSheet = false
            },
            onAddCustomClick = {
                showAddCustomDhikrDialog = true
            },
            onDeleteCustom = {
                viewModel.deleteCustomDhikr(it)
            },
            onDismiss = { showDhikrSheet = false }
        )
    }

    // Add Custom Dhikr Dialog
    if (showAddCustomDhikrDialog) {
        AddCustomDhikrDialog(
            onDismiss = { showAddCustomDhikrDialog = false },
            onAdd = { title, arabic, meaning, target ->
                viewModel.addCustomDhikr(title, arabic, meaning, target)
                showAddCustomDhikrDialog = false
            }
        )
    }

    // Theme Selection Sheet
    if (showThemeSheet) {
        ThemeSelectionSheet(
            selectedTheme = state.selectedTheme,
            onThemeSelect = {
                viewModel.setTheme(it)
                showThemeSheet = false
            },
            onDismiss = { showThemeSheet = false }
        )
    }

    // History Sheet
    if (showHistorySheet) {
        HistorySheet(
            history = state.history,
            totalCount = state.totalLifetimeCount,
            onClearHistory = { viewModel.clearHistory() },
            onDismiss = { showHistorySheet = false }
        )
    }
}

@Composable
fun PhysicalZikirmatikDevice(
    count: Int,
    lapCount: Int,
    target: Int,
    primaryColor: Color,
    secondaryColor: Color,
    beadColor: Color,
    accentColor: Color,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onResetClick: () -> Unit,
    onTargetClick: () -> Unit
) {
    // Outer ergonomic body of the digital tasbih device
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(390.dp)
            .shadow(24.dp, shape = RoundedCornerShape(topStart = 80.dp, topEnd = 80.dp, bottomStart = 110.dp, bottomEnd = 110.dp))
            .clip(RoundedCornerShape(topStart = 80.dp, topEnd = 80.dp, bottomStart = 110.dp, bottomEnd = 110.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        primaryColor,
                        secondaryColor,
                        primaryColor.copy(alpha = 0.9f)
                    )
                )
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 26.dp, start = 24.dp, end = 24.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Digital LCD Display Screen
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(95.dp)
                    .shadow(8.dp, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF0F1713))
                    .border(BorderStroke(2.dp, Color(0xFF2C3E34)), RoundedCornerShape(16.dp))
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Small indicators
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "TUR: $lapCount",
                            color = Color(0xFF68D391),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = if (target > 0) "HEDEF: $target" else "SERBEST",
                            color = Color(0xFF68D391),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    // Main Counter Digits
                    Text(
                        text = String.format("%05d", count),
                        fontSize = 44.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        color = Color(0xFF81E6D9),
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth(),
                        letterSpacing = 4.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Sub-buttons Row (Reset & Decrement)
            Row(
                modifier = Modifier.fillMaxWidth(0.75f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Reset small button
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .shadow(4.dp, CircleShape)
                            .clip(CircleShape)
                            .background(Color(0xFF742A2A))
                            .clickable { onResetClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Sıfırla",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Text(
                        text = "SIFIRLA",
                        fontSize = 9.sp,
                        color = Color.White.copy(alpha = 0.7f),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                // Decrement (-1) small button
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .shadow(4.dp, CircleShape)
                            .clip(CircleShape)
                            .background(Color(0xFF2D3748))
                            .clickable { onDecrement() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Geri Al",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Text(
                        text = "GERİ AL",
                        fontSize = 9.sp,
                        color = Color.White.copy(alpha = 0.7f),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Big Tactile Beads Click Button
            val interactionSource = remember { MutableInteractionSource() }
            val isPressed by interactionSource.collectIsPressedAsState()
            val scale by animateFloatAsState(
                targetValue = if (isPressed) 0.92f else 1.0f,
                animationSpec = spring(dampingRatio = 0.5f, stiffness = 600f),
                label = "press_scale"
            )

            Box(
                modifier = Modifier
                    .size(140.dp)
                    .scale(scale)
                    .shadow(16.dp, CircleShape)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                beadColor.copy(alpha = 0.9f),
                                beadColor,
                                primaryColor
                            )
                        )
                    )
                    .border(BorderStroke(4.dp, accentColor.copy(alpha = 0.8f)), CircleShape)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = onIncrement
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Inner concentric groove ring
                Box(
                    modifier = Modifier
                        .size(105.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    beadColor,
                                    beadColor.copy(alpha = 0.7f)
                                )
                            )
                        )
                        .border(BorderStroke(2.dp, Color.White.copy(alpha = 0.4f)), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "ÇEK",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 22.sp,
                            color = Color(0xFF1A202C)
                        )
                        Text(
                            text = "+1",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFF2D3748)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FullScreenZikirmatik(
    state: ZikirmatikState,
    onTap: () -> Unit,
    onExit: () -> Unit,
    primaryColor: Color,
    accentColor: Color
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1.0f,
        label = "fullscreen_scale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(state.selectedTheme.surfaceHex))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onTap
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .scale(scale),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header with Exit Full Screen Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = state.currentDhikr.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                IconButton(onClick = onExit) {
                    Icon(
                        imageVector = Icons.Default.FullscreenExit,
                        contentDescription = "Tam Ekrandan Çık",
                        tint = accentColor,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            // Big Center Counter
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (state.currentDhikr.arabic.isNotBlank()) {
                    Text(
                        text = state.currentDhikr.arabic,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = accentColor,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Text(
                    text = state.count.toString(),
                    fontSize = 90.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    fontFamily = FontFamily.Monospace
                )

                if (state.target > 0) {
                    Text(
                        text = "Hedef: ${state.target}  •  Tur: ${state.lapCount}",
                        fontSize = 18.sp,
                        color = accentColor
                    )
                } else {
                    Text(
                        text = "Serbest Zikir  •  Tur: ${state.lapCount}",
                        fontSize = 18.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
            }

            // Hint at bottom
            Text(
                text = "Saymak için ekranda herhangi bir yere dokunun",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.5f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun TargetSelectionDialog(
    currentTarget: Int,
    onDismiss: () -> Unit,
    onTargetSelected: (Int) -> Unit
) {
    val predefinedTargets = listOf(33, 99, 100, 500, 1000, 0)
    var customInput by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Zikir Hedefi Belirle") },
        text = {
            Column {
                Text(
                    "Hedefe ulaştığınızda özel titreşim ve ses ile uyarılırsınız.",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(33, 99, 100).forEach { target ->
                        FilterChip(
                            selected = currentTarget == target,
                            onClick = { onTargetSelected(target) },
                            label = { Text(target.toString()) }
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(500, 1000, 0).forEach { target ->
                        FilterChip(
                            selected = currentTarget == target,
                            onClick = { onTargetSelected(target) },
                            label = { Text(if (target == 0) "Serbest (∞)" else target.toString()) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = customInput,
                    onValueChange = { customInput = it.filter { char -> char.isDigit() } },
                    label = { Text("Özel Hedef Sayısı") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val parsed = customInput.toIntOrNull()
                    if (parsed != null && parsed > 0) {
                        onTargetSelected(parsed)
                    }
                },
                enabled = customInput.isNotBlank() && (customInput.toIntOrNull() ?: 0) > 0
            ) {
                Text("Özel Hedefi Uygula")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Kapat")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DhikrSelectionSheet(
    allDhikrs: List<Dhikr>,
    selectedDhikr: Dhikr,
    onSelect: (Dhikr) -> Unit,
    onAddCustomClick: () -> Unit,
    onDeleteCustom: (String) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Zikir Seçimi",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = onAddCustomClick,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Özel Zikir Ekle")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(allDhikrs, key = { it.id }) { dhikr ->
                    val isSelected = dhikr.id == selectedDhikr.id
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelect(dhikr) },
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                            else MaterialTheme.colorScheme.surfaceVariant
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                if (dhikr.arabic.isNotBlank()) {
                                    Text(
                                        text = dhikr.arabic,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                                Text(
                                    text = dhikr.title,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                if (dhikr.meaning.isNotBlank()) {
                                    Text(
                                        text = dhikr.meaning,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Text(
                                    text = if (dhikr.defaultTarget > 0) "Önerilen Hedef: ${dhikr.defaultTarget}" else "Hedef: Serbest",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                            if (dhikr.isCustom) {
                                IconButton(onClick = { onDeleteCustom(dhikr.id) }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Sil",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddCustomDhikrDialog(
    onDismiss: () -> Unit,
    onAdd: (title: String, arabic: String, meaning: String, target: Int) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var arabic by remember { mutableStateOf("") }
    var meaning by remember { mutableStateOf("") }
    var targetText by remember { mutableStateOf("33") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Yeni Zikir Ekle") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Zikir Adı *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = arabic,
                    onValueChange = { arabic = it },
                    label = { Text("Arapça Metin (İsteğe bağlı)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = meaning,
                    onValueChange = { meaning = it },
                    label = { Text("Anlamı / Fazileti (İsteğe bağlı)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = targetText,
                    onValueChange = { targetText = it.filter { char -> char.isDigit() } },
                    label = { Text("Hedef Sayı (0 = Serbest)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val target = targetText.toIntOrNull() ?: 33
                    onAdd(title.trim(), arabic.trim(), meaning.trim(), target)
                },
                enabled = title.isNotBlank()
            ) {
                Text("Ekle")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("İptal")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSelectionSheet(
    selectedTheme: TasbihTheme,
    onThemeSelect: (TasbihTheme) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Zikirmatik Teması Seçin",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(14.dp))

            TasbihTheme.values().forEach { theme ->
                val isSelected = theme == selectedTheme
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onThemeSelect(theme) },
                    colors = CardDefaults.cardColors(containerColor = Color(theme.surfaceHex)),
                    shape = RoundedCornerShape(12.dp),
                    border = if (isSelected) BorderStroke(2.dp, Color(theme.accentHex)) else null
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(Color(theme.primaryHex))
                                    .border(2.dp, Color(theme.accentHex), CircleShape)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = theme.title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Color(theme.accentHex)
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorySheet(
    history: List<com.example.zikirmatik.model.HistoryRecord>,
    totalCount: Long,
    onClearHistory: () -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Zikir Geçmişi",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Toplam Çekilen Zikir: $totalCount",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                if (history.isNotEmpty()) {
                    TextButton(onClick = onClearHistory) {
                        Text("Temizle", color = MaterialTheme.colorScheme.error)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (history.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Henüz tamamlanmış bir zikir kaydı bulunmuyor.",
                        color = MaterialTheme.colorScheme.outline,
                        fontSize = 14.sp
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(history, key = { it.id }) { record ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = record.dhikrTitle,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                                    Text(
                                        text = record.dateText,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.outline
                                    )
                                }
                                Text(
                                    text = "+${record.count}",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
