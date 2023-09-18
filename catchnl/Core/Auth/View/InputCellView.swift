//
//  InputCellView.swift
//  catchnl
//
//  Created by Fatih Toker on 13.09.2023.
//

import SwiftUI

enum InputCellState : String {
    case text = "text"
    case password = "password"
}

struct InputCellView: View {
    var hint: String
    var systemImageName: String?
    var text: Binding<String>
    var state: InputCellState
    
    var body: some View {
        HStack(alignment: .top) {
            if let image = systemImageName {
                Image(systemName: image)
                    .resizable()
                    .scaledToFit()
                    .frame(width: 24, height: 24)
                    .foregroundColor(Color(.systemGray4))
            }
            
            if state == .text {
                TextField(hint, text: text)
            } else {
                SecureField(hint, text: text)
            }
            
        }
        .padding()
        .background(.white)
        
        .overlay(
          RoundedRectangle(cornerRadius: 8)
            .stroke(Color(.systemGray6), lineWidth: 1)
        )
         
    }
}

