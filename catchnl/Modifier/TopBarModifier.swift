//
//  TopBarModifier.swift
//  catchnl
//
//  Created by Fatih Toker on 14.09.2023.
//

import Foundation
import SwiftUI
struct TopBarModifier: ViewModifier {
    
    @EnvironmentObject var viewModel: ContentViewModel
    
    var isFromMain: Bool?
    
    func body(content: Content) -> some View {
        content
            .toolbar {
                ToolbarItem(placement: .principal){
                    Image("logo-dark")
                        .resizable()
                        .scaledToFit()
                        .frame(width: 100)
                }
                
                ToolbarItem(placement: .navigationBarTrailing){
                    Image(systemName: viewModel.state == .info ? "xmark.circle" : "info.circle")
                        .resizable()
                        .scaledToFit()
                        .foregroundColor(.black)
                        .onTapGesture {
                            viewModel.goInfoFromMain = isFromMain ?? viewModel.goInfoFromMain
                            if viewModel.state == .info {
                                if isFromMain ?? viewModel.goInfoFromMain {
                                    viewModel.state = .main
                                } else {
                                    viewModel.state = .notAccepted
                                }
                            } else {
                                viewModel.state = .info
                            }
                             
                            print("isFromMain \(viewModel.goInfoFromMain)")
                        }
                }
            }
            .toolbarBackground(.white, for: ToolbarPlacement.navigationBar)
    }
}
