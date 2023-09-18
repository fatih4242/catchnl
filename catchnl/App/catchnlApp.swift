//
//  catchnlApp.swift
//  catchnl
//
//  Created by Fatih Toker on 12.09.2023.
//

import SwiftUI

@main
struct catchnlApp: App {
    @StateObject var viewModel = ContentViewModel()
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(viewModel)
        }
    }
}
