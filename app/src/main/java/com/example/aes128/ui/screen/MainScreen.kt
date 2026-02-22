package com.example.aes128.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.aes128.util.AESUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    var input by remember { mutableStateOf("") }
    var key by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }

    val clipboardManager = LocalClipboardManager.current
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "AES CRYPTO TOOL",
                            fontWeight = FontWeight.Black,
                            letterSpacing = 2.sp,
                            color = colors.primary
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = colors.surface.copy(alpha = 0.95f)
                    )
                )
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(10.dp))

                // ===== INPUT CARD =====

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = colors.surface
                    ),
                    border = BorderStroke(
                        1.dp,
                        colors.outline.copy(alpha = 0.5f)
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(18.dp)
                    ) {

                        Text(
                            "ENCRYPTION ENGINE",
                            color = colors.primary,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )

                        OutlinedTextField(
                            value = input,
                            onValueChange = { input = it },
                            label = { Text("SOURCE MESSAGE") },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Edit,
                                    null,
                                    tint = colors.primary
                                )
                            },trailingIcon = {
                                if (input.isNotEmpty()) {
                                    IconButton(onClick = { input = "" }) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Clear text",
                                            tint = colors.primary.copy(alpha = 0.7f)
                                        )
                                    }
                                }
                            },
                            shape = RoundedCornerShape(6.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colors.primary,
                                unfocusedBorderColor = colors.outline,
                                cursorColor = colors.primary
                            )
                        )

                        OutlinedTextField(
                            value = key,
                            onValueChange = { key = it },
                            label = { Text("SECRET KEY") },
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = PasswordVisualTransformation(),
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Lock,
                                    null,
                                    tint = colors.primary
                                )
                            },
                            trailingIcon = {
                                if (key.isNotEmpty()) {
                                    IconButton(onClick = { key = "" }) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Clear text",
                                            tint = colors.primary.copy(alpha = 0.7f)
                                        )
                                    }
                                }
                            },
                            shape = RoundedCornerShape(6.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colors.primary,
                                unfocusedBorderColor = colors.outline,
                                cursorColor = colors.primary
                            )
                        )
                    }
                }

                // ===== BUTTONS =====

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {

                    Button(
                        onClick = {
                            if (input.isNotEmpty() && key.isNotEmpty())
                                output = AESUtil.encrypt(input, key)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colors.primary,
                            contentColor = colors.onPrimary
                        )
                    ) {
                        Icon(Icons.Default.CheckCircle, null)
                        Spacer(Modifier.width(8.dp))
                        Text("ENCRYPT", fontWeight = FontWeight.Black, fontSize = 13.sp)
                    }

                    OutlinedButton(
                        onClick = {
                            if (input.isNotEmpty() && key.isNotEmpty())
                                output = AESUtil.decrypt(input, key)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        shape = RoundedCornerShape(6.dp),
                        border = BorderStroke(2.dp, colors.primary),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = colors.primary
                        )
                    ) {
                        Icon(Icons.Default.Refresh, null)
                        Spacer(Modifier.width(8.dp))
                        Text("DECRYPT", fontWeight = FontWeight.Black, fontSize = 13.sp)
                    }
                }

                // ===== OUTPUT =====

                if (output.isNotEmpty()) {

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(6.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = colors.primary.copy(alpha = 0.08f)
                        ),
                        border = BorderStroke(1.dp, colors.primary)
                    ) {

                        Column(modifier = Modifier.padding(18.dp)) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    "OUTPUT TERMINAL",
                                    color = colors.primary,
                                    fontWeight = FontWeight.ExtraBold
                                )

                                IconButton(onClick = {
                                    clipboardManager.setText(
                                        AnnotatedString(output)
                                    )
                                }) {
                                    Icon(
                                        Icons.Default.ContentCopy,
                                        "Copy",
                                        tint = colors.primary
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = output,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp,
                                color = colors.onSurface
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Developed By SeyyedMorty",
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                    fontSize = 13.sp,
                    letterSpacing = 2.5.sp,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(bottom = 40.dp)
                )
            }
        }
    }
}