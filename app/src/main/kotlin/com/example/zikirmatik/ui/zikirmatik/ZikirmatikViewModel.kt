package com.example.zikirmatik.ui.zikirmatik

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.zikirmatik.data.DhikrPreferences
import com.example.zikirmatik.model.Dhikr
import com.example.zikirmatik.model.HistoryRecord
import com.example.zikirmatik.model.PredefinedDhikrs
import com.example.zikirmatik.model.TasbihTheme
import com.example.zikirmatik.util.FeedbackManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ZikirmatikState(
    val currentDhikr: Dhikr = PredefinedDhikrs.list.first(),
    val allDhikrs: List<Dhikr> = PredefinedDhikrs.list,
    val count: Int = 0,
    val lapCount: Int = 0,
    val target: Int = 33,
    val totalLifetimeCount: Long = 0L,
    val isVibrationEnabled: Boolean = true,
    val isSoundEnabled: Boolean = true,
    val isFullScreenEnabled: Boolean = false,
    val selectedTheme: TasbihTheme = TasbihTheme.EMERALD_GOLD,
    val history: List<HistoryRecord> = emptyList(),
    val showTargetReachedBanner: Boolean = false
)

class ZikirmatikViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = DhikrPreferences(application)
    private val feedbackManager = FeedbackManager(application)

    private val _uiState = MutableStateFlow(ZikirmatikState())
    val uiState: StateFlow<ZikirmatikState> = _uiState.asStateFlow()

    init {
        loadInitialState()
    }

    private fun loadInitialState() {
        val customDhikrs = prefs.getCustomDhikrs()
        val allDhikrs = PredefinedDhikrs.list + customDhikrs
        val savedDhikrId = prefs.currentDhikrId
        val activeDhikr = allDhikrs.find { it.id == savedDhikrId } ?: PredefinedDhikrs.list.first()

        _uiState.value = ZikirmatikState(
            currentDhikr = activeDhikr,
            allDhikrs = allDhikrs,
            count = prefs.count,
            lapCount = prefs.lapCount,
            target = prefs.target,
            totalLifetimeCount = prefs.totalLifetimeCount,
            isVibrationEnabled = prefs.isVibrationEnabled,
            isSoundEnabled = prefs.isSoundEnabled,
            isFullScreenEnabled = prefs.isFullScreenEnabled,
            selectedTheme = prefs.selectedTheme,
            history = prefs.getHistory()
        )
    }

    fun increment() {
        val currentState = _uiState.value
        val newCount = currentState.count + 1
        val newTotal = currentState.totalLifetimeCount + 1
        var newLap = currentState.lapCount
        var targetCompleted = false
        var nextCount = newCount

        if (currentState.target > 0 && newCount >= currentState.target) {
            targetCompleted = true
            newLap += 1
            nextCount = 0

            // Save history record for completed round
            val record = HistoryRecord(
                dhikrTitle = currentState.currentDhikr.title,
                count = currentState.target,
                target = currentState.target
            )
            prefs.addHistoryRecord(record)

            if (currentState.isVibrationEnabled) {
                feedbackManager.vibrateTargetReached()
            }
            if (currentState.isSoundEnabled) {
                feedbackManager.playTargetReachedSound()
            }
        } else {
            if (currentState.isVibrationEnabled) {
                feedbackManager.vibrateClick()
            }
            if (currentState.isSoundEnabled) {
                feedbackManager.playClickSound()
            }
        }

        prefs.count = nextCount
        prefs.lapCount = newLap
        prefs.totalLifetimeCount = newTotal

        _uiState.update { state ->
            state.copy(
                count = nextCount,
                lapCount = newLap,
                totalLifetimeCount = newTotal,
                showTargetReachedBanner = targetCompleted,
                history = prefs.getHistory()
            )
        }
    }

    fun decrement() {
        val currentState = _uiState.value
        if (currentState.count > 0) {
            val newCount = currentState.count - 1
            val newTotal = maxOf(0L, currentState.totalLifetimeCount - 1)
            prefs.count = newCount
            prefs.totalLifetimeCount = newTotal
            if (currentState.isVibrationEnabled) feedbackManager.vibrateClick()
            _uiState.update { it.copy(count = newCount, totalLifetimeCount = newTotal) }
        }
    }

    fun dismissTargetBanner() {
        _uiState.update { it.copy(showTargetReachedBanner = false) }
    }

    fun resetCount() {
        val currentState = _uiState.value
        if (currentState.count > 0) {
            val record = HistoryRecord(
                dhikrTitle = currentState.currentDhikr.title,
                count = currentState.count,
                target = currentState.target
            )
            prefs.addHistoryRecord(record)
        }
        prefs.count = 0
        _uiState.update {
            it.copy(
                count = 0,
                history = prefs.getHistory(),
                showTargetReachedBanner = false
            )
        }
    }

    fun resetLaps() {
        prefs.lapCount = 0
        _uiState.update { it.copy(lapCount = 0) }
    }

    fun selectDhikr(dhikr: Dhikr) {
        val newTarget = if (dhikr.defaultTarget > 0) dhikr.defaultTarget else _uiState.value.target
        prefs.currentDhikrId = dhikr.id
        prefs.target = newTarget
        prefs.count = 0
        _uiState.update {
            it.copy(
                currentDhikr = dhikr,
                target = newTarget,
                count = 0,
                showTargetReachedBanner = false
            )
        }
    }

    fun setTarget(newTarget: Int) {
        prefs.target = newTarget
        _uiState.update { it.copy(target = newTarget) }
    }

    fun toggleVibration() {
        val newValue = !_uiState.value.isVibrationEnabled
        prefs.isVibrationEnabled = newValue
        if (newValue) feedbackManager.vibrateClick()
        _uiState.update { it.copy(isVibrationEnabled = newValue) }
    }

    fun toggleSound() {
        val newValue = !_uiState.value.isSoundEnabled
        prefs.isSoundEnabled = newValue
        if (newValue) feedbackManager.playClickSound()
        _uiState.update { it.copy(isSoundEnabled = newValue) }
    }

    fun toggleFullScreen() {
        val newValue = !_uiState.value.isFullScreenEnabled
        prefs.isFullScreenEnabled = newValue
        _uiState.update { it.copy(isFullScreenEnabled = newValue) }
    }

    fun setTheme(theme: TasbihTheme) {
        prefs.selectedTheme = theme
        _uiState.update { it.copy(selectedTheme = theme) }
    }

    fun addCustomDhikr(title: String, arabic: String, meaning: String, target: Int) {
        val customDhikrs = prefs.getCustomDhikrs().toMutableList()
        val newDhikr = Dhikr(
            id = "custom_${System.currentTimeMillis()}",
            title = title,
            arabic = arabic,
            meaning = meaning,
            defaultTarget = target,
            isCustom = true
        )
        customDhikrs.add(newDhikr)
        prefs.saveCustomDhikrs(customDhikrs)
        val allDhikrs = PredefinedDhikrs.list + customDhikrs
        _uiState.update { it.copy(allDhikrs = allDhikrs) }
        selectDhikr(newDhikr)
    }

    fun deleteCustomDhikr(dhikrId: String) {
        val customDhikrs = prefs.getCustomDhikrs().toMutableList()
        customDhikrs.removeAll { it.id == dhikrId }
        prefs.saveCustomDhikrs(customDhikrs)
        val allDhikrs = PredefinedDhikrs.list + customDhikrs
        val currentDhikr = if (_uiState.value.currentDhikr.id == dhikrId) {
            PredefinedDhikrs.list.first()
        } else {
            _uiState.value.currentDhikr
        }
        _uiState.update { it.copy(allDhikrs = allDhikrs, currentDhikr = currentDhikr) }
    }

    fun clearHistory() {
        prefs.clearHistory()
        _uiState.update { it.copy(history = emptyList()) }
    }

    override fun onCleared() {
        super.onCleared()
        feedbackManager.release()
    }
}
