package com.waqas.tracker_presentation.tracker_overview.components

import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.waqas.core_ui.LocalSpacing
import com.waqas.tracker_presentation.components.NutrientsInfo
import com.waqas.tracker_presentation.components.UnitDisplay
import com.waqas.tracker_presentation.tracker_overview.Meal

@Composable
fun ExpandableMeal(
    meal: Meal,
    onToggleClick: () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {

    val spacing = LocalSpacing.current
    val context = LocalContext.current

    Column(modifier = modifier) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggleClick() }
            .padding(spacing.spaceMedium), verticalAlignment = Alignment.CenterVertically

        ) {
            Image(
                painter = painterResource(id = meal.drawableRes),
                contentDescription = meal.name.asString(context)
            )
            Spacer(modifier = Modifier.width(spacing.spaceSmall))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = meal.name.asString(context), style = MaterialTheme.typography.h6
                    )
                    Icon(
                        imageVector = if (meal.isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (meal.isExpanded) {
                            stringResource(id = com.waqas.core.R.string.collapse)
                        } else stringResource(id = com.waqas.core.R.string.extend)

                    )
                }

                Spacer(modifier = Modifier.height(spacing.spaceSmall))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    UnitDisplay(
                        amount = meal.calories,
                        unit = stringResource(id = com.waqas.core.R.string.kcal),
                        amountTextSize = 30.sp
                    )

                    Row {
                        NutrientsInfo(
                            name = stringResource(id = com.waqas.core.R.string.carbs),
                            amount = meal.carbs,
                            unit = stringResource(id = com.waqas.core.R.string.grams)
                        )
                        Spacer(modifier = Modifier.width(spacing.spaceSmall))
                        NutrientsInfo(
                            name = stringResource(id = com.waqas.core.R.string.protein),
                            amount = meal.protein,
                            unit = stringResource(id = com.waqas.core.R.string.grams)
                        )
                        Spacer(modifier = Modifier.width(spacing.spaceSmall))
                        NutrientsInfo(
                            name = stringResource(id = com.waqas.core.R.string.fat),
                            amount = meal.fat,
                            unit = stringResource(id = com.waqas.core.R.string.grams)
                        )
                    }


                }

            }
        }

        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        AnimatedVisibility(visible = meal.isExpanded) {
            content()
        }

    }
}